package org.example.shroom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.shroom.crypto.InitializationVector;

public class ShroomServerCodec extends ChannelInboundHandlerAdapter {
    private final short version;
    private final char subVersion;
    private final byte locale;

    public ShroomServerCodec(short version, char subVersion, byte locale) {
        this.version = version;
        this.subVersion = subVersion;
        this.locale = locale;
    }

    private ShroomHandshake getHandshake() {
        return new ShroomHandshake(version, subVersion, InitializationVector.generateSend(), InitializationVector.generateReceive(), locale);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ShroomHandshake handshake = getHandshake();
        ctx.writeAndFlush(handshake.toByteBuf())
                .addListener(fut -> {
                    if (fut.isSuccess()) {
                        ctx.pipeline().replace(this, ctx.name(), new ShroomHandler(handshake.invert()));
                        ctx.fireChannelActive();
                    } else {
                        ctx.close();
                    }
                });
    }
}
