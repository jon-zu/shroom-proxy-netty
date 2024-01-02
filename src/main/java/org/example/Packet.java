package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCounted;

public class Packet implements ReferenceCounted {
    private final ByteBuf buf;

    public Packet(ByteBuf buf) {
        this.buf = buf;
    }

    public ByteBuf getBuf() {
        return buf;
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
