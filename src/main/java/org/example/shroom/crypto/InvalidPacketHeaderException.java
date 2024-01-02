package org.example.shroom.crypto;

import io.netty.handler.codec.EncoderException;

public class InvalidPacketHeaderException extends EncoderException {
    public InvalidPacketHeaderException(String s, int header) {
        super("Invalid Packet header: " + s + " with header: " + header);
    }
}
