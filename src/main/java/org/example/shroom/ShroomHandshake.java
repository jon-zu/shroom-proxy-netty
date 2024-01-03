package org.example.shroom;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import org.example.shroom.crypto.InitializationVector;

public record ShroomHandshake(
        short version, // 2
        char subVersion, // 5
        InitializationVector ivEnc, // 9
        InitializationVector ivDec, // 13
        int localeCode // 14
) {

    // TODO dynamic handshake len based on subversion
    public static final int HANDSHAKE_LEN =  14;

    static ShroomHandshake fromByteBuf(ByteBuf buf) {
        short len = buf.readShortLE();
        if(len != HANDSHAKE_LEN)
            throw new DecoderException("Invalid Shroom handshake length");

        ByteBuf data = buf.readBytes(len);
        short version = data.readShortLE();
        // TODO: in theory longer subversions are supported
        var subVersionLen = data.readShortLE();
        if (subVersionLen != 1)
            throw new DecoderException("Invalid subversion length");
        char subVersion = (char) data.readByte();

        InitializationVector ivEnc = new InitializationVector(data.readIntLE());
        InitializationVector ivDec = new InitializationVector(data.readIntLE());
        int localeCode = data.readByte();
        return new ShroomHandshake(version, subVersion, ivEnc, ivDec, localeCode);
    }
    public ByteBuf toByteBuf() {
        ByteBuf buf = Unpooled.directBuffer(HANDSHAKE_LEN);
        buf.writeShortLE(HANDSHAKE_LEN);
        buf.writeShortLE(this.version);
        buf.writeShortLE(1);
        buf.writeByte(this.subVersion);
        buf.writeIntLE(this.ivEnc.getInt());
        buf.writeIntLE(this.ivDec.getInt());
        buf.writeByte(this.localeCode);
        return buf;
    }

    public ShroomHandshake invert() {
        return new ShroomHandshake(this.getInvVersion(), this.subVersion, this.ivDec, this.ivEnc, this.localeCode);
    }

    public short getInvVersion() {
        return (short)~this.version;
    }
}
