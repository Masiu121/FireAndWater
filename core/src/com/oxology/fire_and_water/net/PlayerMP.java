package com.oxology.fire_and_water.net;

import com.badlogic.gdx.graphics.Texture;
import com.oxology.fire_and_water.FireAndWater;
import com.oxology.fire_and_water.level.entities.Player;

import java.net.InetAddress;

public class PlayerMP extends Player {
    InetAddress address;
    int port;

    public PlayerMP(FireAndWater main, String username, float x, float y, InetAddress address, int port) {
        super(main, username, x, y);
        this.address = address;
        this.port = port;
    }

    public PlayerMP(FireAndWater main, String username, int x, int y) {
        super(main, username, x, y);
        this.address = null;
        this.port = -1;
    }
}
