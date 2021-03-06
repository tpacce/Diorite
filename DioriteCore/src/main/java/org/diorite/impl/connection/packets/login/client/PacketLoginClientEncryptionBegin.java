package org.diorite.impl.connection.packets.login.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginClientListener;

@PacketClass(id = 0x01, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.SERVERBOUND, size = 260)
public class PacketLoginClientEncryptionBegin extends PacketLoginClient
{
    private byte[] sharedSecret; // 128 bytes + 2 bytes size
    private byte[] verifyToken; // 128 bytes + 2 bytes size

    public PacketLoginClientEncryptionBegin()
    {
    }

    public PacketLoginClientEncryptionBegin(final byte[] sharedSecret, final byte[] verifyToken)
    {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.sharedSecret = data.readByteWord();
        this.verifyToken = data.readByteWord();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByteWord(this.sharedSecret);
        data.writeByteWord(this.verifyToken);
    }

    @Override
    public void handle(final PacketLoginClientListener listener)
    {
        listener.handle(this);
    }

    public byte[] getSharedSecret()
    {
        return this.sharedSecret;
    }

    public void setSharedSecret(final byte[] sharedSecret)
    {
        this.sharedSecret = sharedSecret;
    }

    public byte[] getVerifyToken()
    {
        return this.verifyToken;
    }

    public void setVerifyToken(final byte[] verifyToken)
    {
        this.verifyToken = verifyToken;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sharedSecret", this.sharedSecret).append("verifyToken", this.verifyToken).toString();
    }
}
