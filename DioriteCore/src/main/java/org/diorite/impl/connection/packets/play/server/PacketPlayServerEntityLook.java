package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.impl.entity.EntityImpl;

@PacketClass(id = 0x16, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 8)
public class PacketPlayServerEntityLook extends PacketPlayServer
{
    private int     entityId; // ~5 bytes
    private byte    yaw; // 1 byte
    private byte    pitch; // 1 byte
    private boolean onGround; // 1 byte

    public PacketPlayServerEntityLook()
    {
    }

    public PacketPlayServerEntityLook(final int entityId, final byte yaw, final byte pitch, final boolean onGround)
    {
        this.entityId = entityId;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayServerEntityLook(final int entityId, final float yaw, final float pitch, final boolean onGround)
    {
        this.entityId = entityId;
        this.yaw = (byte) ((yaw * 256.0F) / 360.0F);
        this.pitch = (byte) ((pitch * 256.0F) / 360.0F);
        this.onGround = onGround;
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayServerEntityLook(final EntityImpl entity)
    {
        this.entityId = entity.getId();
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);
        this.onGround = entity.isOnGround();
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.yaw = data.readByte();
        this.pitch = data.readByte();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeByte(this.yaw);
        data.writeByte(this.pitch);
        data.writeBoolean(this.onGround);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    public byte getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final byte yaw)
    {
        this.yaw = yaw;
    }

    public byte getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final byte pitch)
    {
        this.pitch = pitch;
    }

    public boolean isOnGround()
    {
        return this.onGround;
    }

    public void setOnGround(final boolean onGround)
    {
        this.onGround = onGround;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("yaw", this.yaw).append("pitch", this.pitch).append("onGround", this.onGround).toString();
    }
}
