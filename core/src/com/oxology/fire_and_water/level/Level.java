package com.oxology.fire_and_water.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public List<Platform> platforms = new ArrayList<>();

    public void addPlatform(float x, float y, float width, float height, Texture texture) {
        Platform platform = new Platform(x, y, width, height, texture);
        platforms.add(platform);
    }

    public void drawLevel(SpriteBatch batch) {
        for(Platform platform : platforms) {
            batch.draw(platform.texture, platform.x, platform.y, platform.width, platform.height);
        }
    }
}
