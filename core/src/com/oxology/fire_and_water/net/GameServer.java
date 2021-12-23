package com.oxology.fire_and_water.net;

import com.oxology.fire_and_water.FireAndWater;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameServer extends Thread {
    FireAndWater game;
    DatagramSocket socket;

    public GameServer(FireAndWater game) {
        this.game = game;
        try {
            socket = new DatagramSocket(2115);
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
            String message = new String(packet.getData()).trim();
            if(message.equalsIgnoreCase("ping")) {
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
                System.out.println("[CLIENT->SERVER]: " + message);
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        }   catch(IOException e) {
            e.printStackTrace();
        }
    }
}
