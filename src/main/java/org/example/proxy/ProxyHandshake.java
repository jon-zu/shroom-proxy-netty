package org.example.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.net.InetAddress;

public class ProxyHandshake {
    public static final int HANDSHAKE_SIZE = 2 + 10;
    private static final short HANDSHAKE_HEADER = (short)0xFEAB;

    private final int proxyId;
    private final short proxyVersion;
    private final InetAddress remoteAddress;


    public ProxyHandshake(int proxyId, short proxyVersion, InetAddress remoteAddress) {
        this.proxyId = proxyId;
        this.proxyVersion = proxyVersion;
        this.remoteAddress = remoteAddress;
    }


    public ProxyHandshake(ByteBuf buf) throws Exception {
        short hdr = buf.readShort();
        if(hdr != HANDSHAKE_HEADER)
            throw new Exception("Invalid proxy handshake header");

        this.proxyId = buf.readInt();
        this.proxyVersion = buf.readShort();

        byte[] ipBuf = new byte[4];
        buf.readBytes(ipBuf);
        this.remoteAddress = InetAddress.getByAddress(ipBuf);
    }

    public ByteBuf toByteBuf() {
        ByteBuf buf = Unpooled.directBuffer(HANDSHAKE_SIZE);
        buf.writeShort(HANDSHAKE_HEADER);
        buf.writeInt(this.proxyId);
        buf.writeShort(this.proxyVersion);

        byte[] addr = this.remoteAddress.getAddress();
       /*
        TODO
        if(addr.length > 4)
            throw new Exception("Not an IPv4");*/
        buf.writeBytes(addr);
        return buf;
    }

    public int getProxyId() {
        return proxyId;
    }

    public short getProxyVersion() {
        return proxyVersion;
    }

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }
}
