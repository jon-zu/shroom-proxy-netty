package org.example.proxy;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;


import org.example.Packet;

public class ProxyDecoder extends ByteToMessageDecoder {
    int frameLength;
    public ProxyDecoder() {
        this.frameLength = -1;
    }

    private int checkFrameLength(int frameLength) {
        if(frameLength < 0)
            throw new DecoderException("Negative proxy frame");

        if(frameLength > ProxyHandler.MAX_PKT_LEN)
            throw new DecoderException("Too large proxy frame");

        return frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(this.frameLength == -1) {
            if(byteBuf.readableBytes() < 4)
                return;

            this.frameLength = this.checkFrameLength(byteBuf.readInt());
        }

        if(byteBuf.readableBytes() < this.frameLength)
            return;

        var packetBuf = byteBuf.readRetainedSlice(this.frameLength);
        list.add(new Packet(packetBuf));
        this.frameLength = -1;
    }
}
