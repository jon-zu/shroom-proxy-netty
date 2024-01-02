package org.example.shroom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.Packet;
import org.example.shroom.crypto.ShandaCipher;
import org.example.shroom.crypto.ShroomAESCipher;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    private final ShroomAESCipher sendCypher;

    public PacketEncoder(ShroomAESCipher sendCypher) {
        this.sendCypher = sendCypher;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) {
        ByteBuf buf = in.getBuf();
        int len = buf.readableBytes();
        out.writeBytes(getEncodedHeader(len));

        ShandaCipher.encryptData(buf, 0, len);
        sendCypher.crypt(buf, 0, len);
        out.writeBytes(buf);
    }

    private byte[] getEncodedHeader(int length) {
        return sendCypher.getPacketHeader(length);
    }
}
