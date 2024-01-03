package org.example.proxy;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;

import java.util.List;


import io.netty.handler.codec.ReplayingDecoder;
import org.example.packet.Packet;
import org.example.packet.PacketReader;

public class ProxyDecoder extends ReplayingDecoder<Integer> {
    public ProxyDecoder() {
        super(-1);
    }

    private int checkFrameLength(int frameLength) {
        if(frameLength < 0)
            throw new DecoderException("Negative proxy frame");

        if(frameLength > Packet.MAX_PACKET_LEN)
            throw new DecoderException("Too large proxy frame");

        return frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        int frameLength = this.state();
        if(frameLength == -1) {
            frameLength = this.checkFrameLength(byteBuf.readInt());
            this.checkpoint(frameLength);
        }
        var packetBuf = byteBuf.readRetainedSlice(frameLength);
        list.add(new PacketReader(packetBuf));
        this.checkpoint(-1);
    }
}
