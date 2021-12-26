package com.oxology.fire_and_water.net.packets;

import com.oxology.fire_and_water.net.GameClient;
import com.oxology.fire_and_water.net.GameServer;

public abstract class Packet {
    PacketType packetType;

    public enum PacketType {
        CONNECT(00),
        DISCONNECT(01),
        MOVE(02),
        INVALID(-1);

        public int packetType;

        PacketType(int packetType) {
            this.packetType = (byte) packetType;
        }
    }

    public Packet(int packetType) {
        this.packetType = findPacket(packetType);
    }

    public abstract byte[] getData();

    public abstract void writeData(GameServer server);

    public abstract void writeData(GameClient client);

    public String readData(byte[] data) {
        return new String(data).trim().substring(2);
    }

    public static PacketType findPacket(int packetType) {
        for(PacketType type : PacketType.values()) {
            if(type.packetType == packetType)
                return type;
        }
        return PacketType.INVALID;
    }

    public static PacketType findPacket(String packetType) {
        return findPacket(Integer.parseInt(packetType));
    }
}
