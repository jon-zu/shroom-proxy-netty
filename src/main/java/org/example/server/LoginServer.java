package org.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.packet.PacketReader;

public abstract class LoginServer<T extends ShroomChannel> extends SimpleChannelInboundHandler<PacketReader> {

    /// Creates a new channel from the initial packet and context
    protected abstract T createChannel(ChannelHandlerContext ctx);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        T channel = createChannel(ctx);
        ctx.pipeline().replace(this, ctx.name(), channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketReader msg) throws Exception {
        throw new Exception("Handler should be removed from pipeline before this is called");
    }

}
