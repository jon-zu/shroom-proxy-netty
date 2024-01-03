package org.example.shroom.crypto;

public class InitializationVector {
    private final int iv;

    public InitializationVector(byte[] bytes) {
        this.iv = Util.littleEndianToInt(bytes);
    }

    public InitializationVector(int iv) {
        this.iv = iv;
    }

    public int getInt() {
        return this.iv;
    }

    public byte[] getBytes() {
        return Util.intToLittleEndian(this.iv);
    }


    public static InitializationVector generateSend() {
        byte[] ivSend = {82, 48, 120, getRandomByte()};
        return new InitializationVector(ivSend);
    }

    public static InitializationVector generateReceive() {
        byte[] ivRecv = {70, 114, 122, getRandomByte()};
        return new InitializationVector(ivRecv);
    }

    private static byte getRandomByte() {
        return (byte) (Math.random() * 255);
    }
}
