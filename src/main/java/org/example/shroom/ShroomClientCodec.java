package org.example.shroom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ShroomClientCodec extends ReplayingDecoder<Void> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) {
        var handshake = ShroomHandshake.fromByteBuf(byteBuf);
        if (byteBuf.isReadable())
            list.add(byteBuf.readBytes(actualReadableBytes()));

        ctx.pipeline().replace(this, ctx.name(), new ShroomHandler(handshake));
        ctx.fireChannelActive();
    }
}
