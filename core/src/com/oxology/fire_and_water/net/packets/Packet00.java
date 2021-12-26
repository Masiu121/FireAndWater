package com.oxology.fire_and_water.net.packets;

import com.oxology.fire_and_water.net.GameClient;
import com.oxology.fire_and_water.net.GameServer;

public class Packet00 extends Packet {
    String username;
    float x, y;

    public Packet00(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Float.parseFloat(dataArray[1]);
        this.y = Float.parseFloat(dataArray[2]);
    }

    public Packet00(String username, float x, float y) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public byte[] getData() {
        return ("00" + username + "," + this.x + "," + this.y).getBytes();
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
