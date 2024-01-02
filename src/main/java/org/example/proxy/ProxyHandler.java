package org.example.proxy;

import io.netty.channel.*;

import java.net.InetAddress;

public class ProxyHandler extends CombinedChannelDuplexHandler<ProxyDecoder, ProxyEncoder> {
    public static final int MAX_PKT_LEN = 2 * 4096;
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
