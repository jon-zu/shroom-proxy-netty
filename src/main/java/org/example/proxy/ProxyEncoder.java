package org.example.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.packet.Packet;
import org.example.packet.PacketWriter;

public class ProxyEncoder extends MessageToByteEncoder<PacketWriter> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PacketWriter packet, ByteBuf byteBuf) {
        var buf = packet.getBuf();
        var len = packet.len();
        if(len > Packet.MAX_PACKET_LEN)
            throw new EncoderException("Proxy encoder max frame size");

        byteBuf.writeInt(len);
        byteBuf.writeBytes(buf);
    }
}
