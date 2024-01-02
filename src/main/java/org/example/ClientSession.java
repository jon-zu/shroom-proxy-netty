package org.example;


import java.net.InetAddress;

public abstract class ClientSession {
    private final InetAddress remoteAddr;
    private final IPacketHandler packetHandler;

    public ClientSession(InetAddress addr, IPacketHandler packetHandler) {
        this.remoteAddr = addr;
        this.packetHandler = packetHandler;
    }

    public abstract void sendPacket(Packet packet);
    public void handlePacket(Packet packet) {
        this.packetHandler.handle(packet);
    }


    public InetAddress getRemoteAddr() {
        return remoteAddr;
    }
}
