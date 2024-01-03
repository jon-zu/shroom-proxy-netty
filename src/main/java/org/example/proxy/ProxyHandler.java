package org.example.proxy;

import io.netty.channel.CombinedChannelDuplexHandler;

public class ProxyHandler extends CombinedChannelDuplexHandler<ProxyDecoder, ProxyEncoder> {
    private final ProxyHandshake handshake;

    public ProxyHandler(ProxyHandshake handshake) {
        super(
                new ProxyDecoder(),
                new ProxyEncoder()
        );
        this.handshake = handshake;
    }


    public ProxyHandshake getHandshake() {
        return handshake;
    }
}
