package org.example.packet;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

public class PacketReader extends Packet {
    public PacketReader(ByteBuf buf) {
        super(buf);
    }

    public PacketWriter toPacketWriter() {
        return new PacketWriter(buf.retain());
    }

    public boolean readBoolean() {
        return buf.readBoolean();
    }

    public byte readByte() {
        return buf.readByte();
    }

    public short readShort() {
        return buf.readShortLE();
    }

    public int readInt() {
        return buf.readIntLE();
    }

    public long readLong() {
        return buf.readLongLE();
    }

    public String readString() {
        short len = this.readShort();
        if (len < 0 || len > MAX_STRING_LEN)
            throw new DecoderException("Invalid string length: " + len);

        if (len == 0)
            return "";

        ByteBuf strBuf = buf.readBytes(len);
        return strBuf.toString(CHARSET);
    }

    public FileTime readFileTime() {
        return new FileTime(this.readLong());
    }

    public Vec2 readVec2() {
        return new Vec2(this.readShort(), this.readShort());
    }
}
