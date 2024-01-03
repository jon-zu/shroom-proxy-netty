package org.example.shroom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.ReplayingDecoder;
import org.example.packet.Packet;
import org.example.packet.PacketReader;
import org.example.shroom.crypto.ShandaCipher;
import org.example.shroom.crypto.ShroomAESCipher;

import java.util.List;

public class PacketDecoder extends ReplayingDecoder<Integer> {
    private final ShroomAESCipher receiveCypher;

    public PacketDecoder(ShroomAESCipher receiveCypher) {
        super(-1);

        this.receiveCypher = receiveCypher;
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) {
        int packetLength = state();
        if(packetLength == -1) {
            final int packetLen = receiveCypher.decodeHeader(in.readIntLE());
            checkpoint(packetLen);
            if(packetLen < 0 || packetLen > Packet.MAX_PACKET_LEN)
                throw new EncoderException("Packet lenght out of limits");
            packetLength = packetLen;
        }

        ByteBuf pktBuf = in.readRetainedSlice(packetLength);
        this.checkpoint(-1);
        receiveCypher.crypt(pktBuf, 0, packetLength);
        ShandaCipher.decryptData(pktBuf,0, packetLength);
        out.add(new PacketReader(pktBuf));
    }
}