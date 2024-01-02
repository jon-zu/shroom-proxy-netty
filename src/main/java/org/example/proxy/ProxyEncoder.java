package org.example.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.Packet;

public class ProxyEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        var buf = packet.getBuf();
        var len = buf.readableBytes();
        if(len > ProxyHandler.MAX_PKT_LEN)
            throw new EncoderException("Proxy encoder max frame size");

        byteBuf.writeInt(len);
        byteBuf.writeBytes(buf);
    }
}
