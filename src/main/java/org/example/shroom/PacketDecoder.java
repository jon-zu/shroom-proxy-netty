package org.example.shroom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.ReplayingDecoder;
import org.example.Packet;
import org.example.shroom.crypto.InvalidPacketHeaderException;
import org.example.shroom.crypto.ShandaCipher;
import org.example.shroom.crypto.ShroomAESCipher;

import java.util.List;

public class PacketDecoder extends ReplayingDecoder<Integer> {
    private final ShroomAESCipher receiveCypher;

    public PacketDecoder(ShroomAESCipher receiveCypher) {
        this.receiveCypher = receiveCypher;
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) {
        int packetLength = state();
        if(packetLength == -1) {
            final int header = in.readInt();
            if (!receiveCypher.isValidHeader(header)) {
                throw new InvalidPacketHeaderException("Attempted to decode a packet with an invalid header", header);
            }
            packetLength = decodePacketLength(header);
            if(packetLength < 0 || packetLength > 2*4096)
                throw new EncoderException("Packet lenght out of limits");
            checkpoint(packetLength);
        }

        ByteBuf pktBuf = in.readSlice(packetLength);
        this.checkpoint(-1);
        receiveCypher.crypt(pktBuf, 0, packetLength);
        ShandaCipher.decryptData(pktBuf,0, packetLength);
        out.add(new Packet(pktBuf));
    }

    /**
     * @param header Packet header - the first 4 bytes of the packet
     * @return Packet size in bytes
     */
    private static int decodePacketLength(byte[] header) {
        return (((header[1] ^ header[3]) & 0xFF) << 8) | ((header[0] ^ header[2]) & 0xFF);
    }

    private static int decodePacketLength(int header) {
        int length = ((header >>> 16) ^ (header & 0xFFFF));
        length = ((length << 8) & 0xFF00) | ((length >>> 8) & 0xFF);
        return length;
    }
}