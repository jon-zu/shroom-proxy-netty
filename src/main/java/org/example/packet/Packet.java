package org.example.packet;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCounted;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Packet implements ReferenceCounted {
    public static final int MAX_STRING_LEN = 2048;
    public static final int MAX_PACKET_LEN = 2 * 4096;

    // TODO verify this, WZ files are 100% Latin-1
    protected static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    protected final ByteBuf buf;

    protected Packet(ByteBuf buf) {
        this.buf = buf;
    }

    public ByteBuf getBuf() {
        return buf;
    }

    public int len() {
        return buf.readableBytes();
    }

    @Override
    public int refCnt() {
        return this.buf.refCnt();
    }

    @Override
    public ReferenceCounted retain() {
        this.buf.retain();
        return this;
    }

    @Override
    public ReferenceCounted retain(int i) {
        this.buf.retain(i);
        return this;
    }

    @Override
    public ReferenceCounted touch() {
        this.buf.touch();
        return this;
    }

    @Override
    public ReferenceCounted touch(Object o) {
        this.buf.touch(o);
        return this;
    }

    @Override
    public boolean release() {
        return this.buf.release();
    }

    @Override
    public boolean release(int i) {
        return this.buf.release(i);
    }
}
