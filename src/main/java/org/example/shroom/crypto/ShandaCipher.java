/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.example.shroom.crypto;

import io.netty.buffer.ByteBuf;

public class ShandaCipher {
    private static byte rollLeft(byte in, int count) {
        int tmp = (int) in & 0xFF;
        tmp = tmp << (count % 8);
        return (byte) ((tmp & 0xFF) | (tmp >> 8));
    }

    private static byte rollRight(byte in, int count) {
        int tmp = (int) in & 0xFF;
        tmp = (tmp << 8) >>> (count % 8);

        return (byte) ((tmp & 0xFF) | (tmp >>> 8));
    }

    public static void encryptData(ByteBuf buf, int offset, int length) {
        for (int j = 0; j < 6; j++) {
            byte remember = 0;
            byte dataLength = (byte) (length & 0xFF);
            if (j % 2 == 0) {
                for (int i = 0; i < length; i++) {
                    int m = i + offset;
                    byte cur = buf.getByte(m);
                    cur = rollLeft(cur, 3);
                    cur += dataLength;
                    cur ^= remember;
                    remember = cur;
                    cur = rollRight(cur, (int) dataLength & 0xFF);
                    cur = ((byte) ((~cur) & 0xFF));
                    cur += 0x48;
                    dataLength--;
                    buf.setByte(m, cur);
                }
            } else {
                for (int i = length - 1; i >= 0; i--) {
                    int m = i + offset;
                    byte cur = buf.getByte(m);
                    cur = rollLeft(cur, 4);
                    cur += dataLength;
                    cur ^= remember;
                    remember = cur;
                    cur ^= 0x13;
                    cur = rollRight(cur, 3);
                    dataLength--;
                    buf.setByte(m, cur);
                }
            }
        }
    }

    public static void decryptData(ByteBuf buf, int offset, int length) {
        for (int j = 1; j <= 6; j++) {
            byte remember = 0;
            byte dataLength = (byte) (length & 0xFF);
            byte nextRemember;
            if (j % 2 == 0) {
                for (int i = 0; i < length; i++) {
                    int m = i + offset;
                    byte cur = buf.getByte(m);
                    cur -= 0x48;
                    cur = ((byte) ((~cur) & 0xFF));
                    cur = rollLeft(cur, (int) dataLength & 0xFF);
                    nextRemember = cur;
                    cur ^= remember;
                    remember = nextRemember;
                    cur -= dataLength;
                    cur = rollRight(cur, 3);
                    buf.setByte(m, cur);
                    dataLength--;
                }
            } else {
                for (int i = length - 1; i >= 0; i--) {
                    int m = i + offset;
                    byte cur = buf.getByte(m);
                    cur = rollLeft(cur, 3);
                    cur ^= 0x13;
                    nextRemember = cur;
                    cur ^= remember;
                    remember = nextRemember;
                    cur -= dataLength;
                    cur = rollRight(cur, 4);
                    buf.setByte(m, cur);
                    dataLength--;
                }
            }
        }
    }
}
