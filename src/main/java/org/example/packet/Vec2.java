package org.example.packet;

public class Vec2 {
    short x;
    short y;

    public Vec2(short x, short y) {
        this.x = x;
        this.y = y;
    }

    public Vec2() {
        this.x = 0;
        this.y = 0;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public void setX(short x) {
        this.x = x;
    }

    public void setY(short y) {
        this.y = y;
    }
}