package org.diorite.impl.server.connection;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.ConnectionHandler;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.utils.LazyInitVar;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultExecutorServiceFactory;

public class ServerConnection extends Thread implements ConnectionHandler
{
    public static final int                              MILLIS                         = 500;
    public final        LazyInitVar<NioEventLoopGroup>   lazyInitNioEventLoopGroup      = new LazyInitVar<NioEventLoopGroup>()
    {
        @Override
        protected NioEventLoopGroup init()
        {
            return new NioEventLoopGroup(0, new DefaultExecutorServiceFactory("Netty Client"));
        }
    };
    public final        LazyInitVar<EpollEventLoopGroup> lazyInitNioEpollEventLoopGroup = new LazyInitVar<EpollEventLoopGroup>()
    {
        @Override
        protected EpollEventLoopGroup init()
        {
            return new EpollEventLoopGroup(0, new DefaultExecutorServiceFactory("Netty Epoll Client"));
        }
    };

    private final AttributeKey<EnumProtocol>     protocolKey = AttributeKey.valueOf("protocol");
    private final Collection<NetworkManager> connections = Collections.synchronizedSet(Sets.newConcurrentHashSet());
    private final DioriteCore   core;
    private       ChannelFuture channelFuture;

    public ServerConnection(final DioriteCore core)
    {
        this.core = core;
        this.setDaemon(true);
        this.setName("{Diorite|SrvCon}");
    }

    @Override
    public AttributeKey<EnumProtocol> getProtocolKey()
    {
        return this.protocolKey;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public void init(final InetAddress address, final int port, final boolean useEpoll)
    {
        final Class<? extends ServerSocketChannel> socketChannelClass;
        final LazyInitVar<? extends EventLoopGroup> lazyInit;
        if ((Epoll.isAvailable()) && useEpoll)
        {
            socketChannelClass = EpollServerSocketChannel.class;
            lazyInit = this.lazyInitNioEpollEventLoopGroup;
            CoreMain.debug("[Netty] Using epoll channel type");
        }
        else
        {
            socketChannelClass = NioServerSocketChannel.class;
            lazyInit = this.lazyInitNioEventLoopGroup;
            CoreMain.debug("[Netty] Using default channel type");
        }
        this.channelFuture = new ServerBootstrap().channel(socketChannelClass).childHandler(new ServerConnectionChannel(this)).group(lazyInit.get()).localAddress(address, port).bind().syncUninterruptibly();
    }

    @Override
    public void close()
    {
        try
        {
            this.channelFuture.channel().close().sync();
        } catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while (this.core.isRunning())
        {
            this.update();
            try
            {
                Thread.sleep(MILLIS);
            } catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void remove(final CoreNetworkManager networkManager)
    {
        this.connections.remove(networkManager);
    }

    public void update()
    {
        final Iterator<NetworkManager> iterator = this.connections.iterator();
        while (iterator.hasNext())
        {
            final CoreNetworkManager networkManager = iterator.next();
            networkManager.checkAlive();
            if (! networkManager.hasNoChannel())
            {
                if (! networkManager.isChannelOpen())
                {
                    if (! networkManager.isPreparing())
                    {
                        iterator.remove();
                        networkManager.checkConnection();
                    }
                }
                else
                {
                    try
                    {
                        networkManager.update();
                    } catch (final Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }

    public ChannelFuture getChannelFuture()
    {
        return this.channelFuture;
    }

    public Collection<NetworkManager> getConnections()
    {
        return this.connections;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("protocolKey", this.protocolKey).append("connections", this.connections).append("channelFuture", this.channelFuture).append("server", this.core).toString();
    }
}
