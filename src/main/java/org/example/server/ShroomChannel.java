package org.example.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.packet.PacketReader;
import org.example.packet.PacketWriter;

public abstract class ShroomChannel extends SimpleChannelInboundHandler<PacketReader>  {
    private final Channel channel;

    public ShroomChannel(ChannelHandlerContext ctx) {
        super(true);
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketReader msg) {
        this.handlePacket(msg);
    }

    public void sendPacket(PacketWriter packet) {
        channel.writeAndFlush(packet);
    }

    protected abstract void handlePacket(PacketReader pkt);
}
