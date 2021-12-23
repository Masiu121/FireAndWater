package com.oxology.fire_and_water.level.entities;

import java.util.UUID;

public class Player extends Entity {
    String username;

    public Player(String username) {
        super(UUID.nameUUIDFromBytes(("Player:" + username).getBytes()), 20, 20);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
