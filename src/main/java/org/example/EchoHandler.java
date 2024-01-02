package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoHandler extends SimpleChannelInboundHandler<Packet> {
    public EchoHandler() {
        super(false);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
            System.out.println("Echoing packet with size: " + msg.getBuf().readableBytes());
            ctx.writeAndFlush(msg);

    }
}
