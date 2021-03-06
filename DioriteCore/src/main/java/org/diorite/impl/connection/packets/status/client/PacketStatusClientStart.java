package org.diorite.impl.connection.packets.status.client;

import java.io.IOException;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.status.PacketStatusClientListener;

@PacketClass(id = 0x00, protocol = EnumProtocol.STATUS, direction = EnumProtocolDirection.SERVERBOUND, size = 0)
public class PacketStatusClientStart extends PacketStatusClient
{

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void handle(final PacketStatusClientListener listener)
    {
        listener.handle(this);
    }
}