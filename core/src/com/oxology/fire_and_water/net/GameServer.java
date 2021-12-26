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
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {
    FireAndWater game;
    DatagramSocket socket;
    List<PlayerMP> connectedPlayer;

    public GameServer(FireAndWater game) {
        this.game = game;
        try {
            socket = new DatagramSocket(2115);
        } catch(IOException e) {
            e.printStackTrace();
        }
        connectedPlayer = new ArrayList<>();
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
        Packet p;
        String message = new String(packet.getData()).trim();
        PacketType type = Packet.findPacket(message.substring(0, 2));
        switch(type) {
            case CONNECT:
                p = new Packet00(packet.getData());
                System.out.println(((Packet00) p).getUsername() + " [" + packet.getAddress() + ":" + packet.getPort() + "] has connected to the server");
                PlayerMP playerMP = new PlayerMP(game, ((Packet00) p).getUsername(), ((Packet00) p).getX(), ((Packet00) p).getY(), packet.getAddress(), packet.getPort());
                addConnection((Packet00) p, playerMP);
                break;
            case DISCONNECT:
                p = new Packet01(packet.getData());
                System.out.println(((Packet01) p).getUsername() + " [" + packet.getAddress() + ":" + packet.getPort() + "] has disconnected from the server");
                removeConnection((Packet01) p);
                break;
        }
    }

    public void addConnection(Packet00 packet, PlayerMP playerMP) {
        boolean alreadyConnected = false;
        for(PlayerMP player : connectedPlayer) {
            if(packet.getUsername().equals(player.getUsername())) {
                if(player.address == null)
                    player.address = playerMP.address;
                if(player.port == -1)
                    player.port = playerMP.port;
                alreadyConnected = true;
            } else {
                sendData(packet.getData(), player.address, player.port);

                packet = new Packet00(player.getUsername(), player.x, player.y);
                sendData(packet.getData(), playerMP.address, playerMP.port);
            }
        }
        if(!alreadyConnected) {
            connectedPlayer.add(playerMP);
        }
    }

    public void removeConnection(Packet01 packet01) {
        int playerIndex = getPlayerIndex(packet01.getUsername());
        if(playerIndex != -1) {
            connectedPlayer.remove(getPlayerIndex(packet01.getUsername()));
            packet01.writeData(this);
        }
    }

    public PlayerMP getPlayer(String username) {
        for(PlayerMP playerMP : connectedPlayer) {
            if(playerMP.getUsername().equals(username))
                return playerMP;
        }
        return null;
    }

    public int getPlayerIndex(String username) {
        for(int i = 0; i < connectedPlayer.size(); i++) {
            if (connectedPlayer.get(i).getUsername().equals(username))
                return i;
        }
        return -1;
    }

    public void sendDataToAllClients(byte[] data) {
        for(PlayerMP player : connectedPlayer) {
            sendData(data, player.address, player.port);
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
