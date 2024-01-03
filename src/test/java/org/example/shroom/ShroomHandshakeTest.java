package org.example.shroom;

import org.example.shroom.crypto.InitializationVector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShroomHandshakeTest {
    @Test
    void checkVersion() {
        ShroomHandshake hshake = new ShroomHandshake((short)83, '1', new InitializationVector(1), new InitializationVector(2), 1);
        assertEquals((short)83, hshake.version());
        assertEquals((short)~83, hshake.getInvVersion());

        ShroomHandshake hshake2 = hshake.invert();
        assertEquals((short)83, hshake2.getInvVersion());
        assertEquals((short)~83, hshake2.version());
    }

}