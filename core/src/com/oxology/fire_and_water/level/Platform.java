package com.oxology.fire_and_water.level;

import com.badlogic.gdx.graphics.Texture;

public class Platform {
    public float x, y;
    public float width, height;
    public Texture texture;

    public Platform(float x, float y, float width, float height, Texture texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }
}
