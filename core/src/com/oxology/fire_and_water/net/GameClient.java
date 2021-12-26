package com.oxology.fire_and_water.net;

import com.oxology.fire_and_water.FireAndWater;
import com.oxology.fire_and_water.net.packets.Packet;
import com.oxology.fire_and_water.net.packets.Packet.PacketType;
import com.oxology.fire_and_water.net.packets.Packet00;
import com.oxology.fire_and_water.net.packets.Packet01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameClient extends Thread {
    InetAddress ipAddress;
    FireAndWater game;
    DatagramSocket socket;

    public GameClient(FireAndWater game, String ipAddress) {
        this.game = game;
        try {
            socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch(IOException e) {
                e.printStackTrace();
            }
            parsePacket(packet);
        }
    }

    public void parsePacket(DatagramPacket packet) {
        Packet p = null;
        String message = new String(packet.getData()).trim();
        PacketType type = Packet.findPacket(message.substring(0, 2));
        switch(type.packetType) {
            case 00:
                p = new Packet00(packet.getData());
                System.out.println(((Packet00) p).getUsername() + " has joined the game");
                PlayerMP playerMP = new PlayerMP(game, ((Packet00) p).getUsername(), ((Packet00) p).getX(), ((Packet00) p).getY(), packet.getAddress(), packet.getPort());
                game.level.addEntity(playerMP);
                break;
            case 01:
                p = new Packet01(packet.getData());
                System.out.println(((Packet01) p).getUsername() + " has left the game");
                game.level.removePlayer(((Packet01) p).getUsername());
                break;
        }
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 2115);
        try {
            socket.send(packet);
        }   catch(IOException e) {
            e.printStackTrace();
        }
    }
}
