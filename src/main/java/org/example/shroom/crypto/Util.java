package org.example.shroom.crypto;

public class Util {
    // Convert short to little-endian byte array
    public static byte[] shortToLittleEndian(short value) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (value & 0xFF);
        bytes[1] = (byte) ((value >> 8) & 0xFF);
        return bytes;
    }

    // Convert little-endian byte array to short
    public static short littleEndianToShort(byte[] bytes) {
        if (bytes.length < 2) {
            throw new IllegalArgumentException("Byte array length must be at least 2");
        }
        return (short) ((bytes[1] << 8) | (bytes[0] & 0xFF));
    }

    // Convert int to little-endian byte array
    public static byte[] intToLittleEndian(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value & 0xFF);
        bytes[1] = (byte) ((value >> 8) & 0xFF);
        bytes[2] = (byte) ((value >> 16) & 0xFF);
        bytes[3] = (byte) ((value >> 24) & 0xFF);
        return bytes;
    }

    // Convert little-endian byte array to int
    public static int littleEndianToInt(byte[] bytes) {
        if (bytes.length < 4) {
            throw new IllegalArgumentException("Byte array length must be at least 4");
        }
        return ((bytes[3] & 0xFF) << 24) |
                ((bytes[2] & 0xFF) << 16) |
                ((bytes[1] & 0xFF) << 8) |
                (bytes[0] & 0xFF);
    }


    public static byte byteRotateLeft(byte in, int count) {
        int tmp = (int) in & 0xFF;
        tmp = tmp << (count % 8);
        return (byte) ((tmp & 0xFF) | (tmp >> 8));
    }

    public static byte byteRotateRight(byte in, int count) {
        int tmp = (int) in & 0xFF;
        tmp = (tmp << 8) >>> (count % 8);

        return (byte) ((tmp & 0xFF) | (tmp >>> 8));
    }

}
