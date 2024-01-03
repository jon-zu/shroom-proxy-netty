package org.example.shroom;

import io.netty.channel.CombinedChannelDuplexHandler;
import org.example.shroom.crypto.ShroomAESCipher;

public class  ShroomHandler extends CombinedChannelDuplexHandler<PacketDecoder, PacketEncoder> {
    private final ShroomHandshake handshake;

    public ShroomHandler(ShroomHandshake handshake) {
        super(
                new PacketDecoder(new ShroomAESCipher(handshake.ivDec(), handshake.getInvVersion())),
                new PacketEncoder(new ShroomAESCipher(handshake.ivEnc(), handshake.version()))
        );
        this.handshake = handshake;
    }

    public ShroomHandshake getHandshake () {
        return handshake;
    }
}
