package com.oxology.fire_and_water.net;

import com.oxology.fire_and_water.FireAndWater;

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
            String message = new String(packet.getData()).trim();
            System.out.println("[SERVER->CLIENT]: " + message);
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
