package org.example;

import io.netty.channel.ChannelHandlerContext;
import org.example.packet.PacketReader;
import org.example.server.ShroomChannel;

public class EchoChannelHandler extends ShroomChannel {
    public EchoChannelHandler(ChannelHandlerContext ctx) {
        super(ctx);
    }

    @Override
    protected void handlePacket(PacketReader pkt) {
        this.sendPacket(pkt.toPacketWriter());
    }
}
