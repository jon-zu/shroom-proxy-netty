package org.example.packet;

import io.netty.buffer.ByteBuf;

public class PacketWriter extends Packet {
    public PacketWriter(ByteBuf buf) {
        super(buf);
    }

    public void writeBoolean(boolean b) {
        buf.writeBoolean(b);
    }

    public void writeByte(byte b) {
        buf.writeByte(b);
    }

    public void writeShort(short s) {
        buf.writeShortLE(s);
    }

    public void writeInt(int i) {
        buf.writeIntLE(i);
    }

    public void writeLong(long l) {
        buf.writeLongLE(l);
    }

    public void writeString(String s) {
        if (s.length() > MAX_STRING_LEN)
            throw new IllegalArgumentException("String too long: " + s.length());

        this.writeShort((short)s.length());
        buf.writeCharSequence(s, CHARSET);
    }

    public void writeVec2(Vec2 v) {
        this.writeShort(v.getX());
        this.writeShort(v.getY());
    }

    public void writeFileTime(FileTime ft) {
        this.writeLong(ft.getFileTime());
    }

    public void writeBytes(byte[] bytes) {
        buf.writeBytes(bytes);
    }
}
