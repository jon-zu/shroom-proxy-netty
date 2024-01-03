package org.example.proxy;


import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProxyHandshakeTest {
    ProxyHandshake parse(CharSequence s)  throws Exception  {
        var buf = Unpooled.copiedBuffer(HexFormat.of().parseHex(s));
        return new ProxyHandshake(buf);
    }


    @Test
    void parse() throws Exception {
        var handshake = parse("FEAB123456789ABC7F000001");
        assertEquals(0x12345678, handshake.getProxyId());
        assertEquals((short)0x9ABC, handshake.getProxyVersion()); // TODO: Why do i have to cast?
        assertEquals(InetAddress.getByName("127.0.0.1"), handshake.getRemoteAddress());
    }

    @Test
    void failedParse() {
        // Wrong header
        assertThrows(Exception.class, () -> parse("FFFF123456789ABC7F000001"));
        // Incomplete
        assertThrows(Exception.class, () -> parse("FEAB"));
    }
}