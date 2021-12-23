package com.oxology.fire_and_water.level.entities;

import java.util.UUID;

public class Entity {
    UUID uuid;
    int x, y;
    int direction; //0 - right, 1 - left, 2 - up, 3 - down

    public Entity(int x, int y) {
        this.uuid = UUID.randomUUID();
        direction = 0;
        this.x = x;
        this.y = y;
    }

    public Entity(UUID uuid, int x, int y) {
        this.uuid = uuid;
        direction = 0;
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        if(this.x > x)
            direction = 1;
        else
            direction = 0;

        if(this.y > y)
            direction = 3;
        else
            direction = 2;

        this.x = x;
        this.y = y;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
