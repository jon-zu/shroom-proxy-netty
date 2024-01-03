package org.example.shroom.crypto;

import io.netty.handler.codec.DecoderException;

public class InvalidPacketHeaderException extends DecoderException {
    public InvalidPacketHeaderException(String s, int header) {
        super("Invalid Packet header: " + s + " with header: " + header);
    }
}
