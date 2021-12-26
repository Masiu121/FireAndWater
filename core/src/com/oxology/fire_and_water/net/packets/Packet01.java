package com.oxology.fire_and_water.net.packets;

import com.oxology.fire_and_water.net.GameClient;
import com.oxology.fire_and_water.net.GameServer;

public class Packet01 extends Packet {
    String username;

    public Packet01(byte[] data) {
        super(01);
        this.username = readData(data);
    }

    public Packet01(String username) {
        super(01);
        this.username = username;
    }

    public byte[] getData() {
        return ("01" + username).getBytes();
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    public String getUsername() {
        return username;
    }
}