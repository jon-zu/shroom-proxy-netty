package org.example;

import io.netty.channel.ChannelHandlerContext;
import org.example.server.LoginServer;

public class EchoHandler extends LoginServer<EchoChannelHandler> {
    public EchoHandler() {
    }

    @Override
    protected EchoChannelHandler createChannel(ChannelHandlerContext ctx) {
        return new EchoChannelHandler(ctx);
    }
}
