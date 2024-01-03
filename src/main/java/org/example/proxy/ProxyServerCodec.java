package org.example.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ProxyServerCodec extends ReplayingDecoder<Void> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        var handshake = new ProxyHandshake(byteBuf);
        if (byteBuf.isReadable())
            list.add(byteBuf.readBytes(actualReadableBytes()));

        ctx.pipeline().replace(this, ctx.name(), new ProxyHandler(handshake));
        ctx.fireChannelActive();
    }
}
