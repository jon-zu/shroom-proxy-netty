package org.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.packet.PacketReader;

public abstract class GameServer<T extends ShroomChannel> extends SimpleChannelInboundHandler<PacketReader> {

    /// Creates a new channel from the initial packet and context
    protected abstract T createChannel(ChannelHandlerContext ctx, PacketReader pkt);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketReader msg) {
        T channel = createChannel(ctx, msg);
        ctx.pipeline().replace(this, ctx.name(), channel);
    }

}
