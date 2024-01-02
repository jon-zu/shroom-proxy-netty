package org.example.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;

public class ProxyClientCodec extends ChannelInboundHandlerAdapter {
    private final ProxyHandshake handshake;

    public ProxyClientCodec(ProxyHandshake handshake) {
        this.handshake = handshake;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof SslHandshakeCompletionEvent handshakeEvent) {
            if (handshakeEvent.isSuccess()) {
                sendCustomHandshake(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    private void sendCustomHandshake(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(this.handshake.toByteBuf());
    }
}
