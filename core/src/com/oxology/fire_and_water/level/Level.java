package com.oxology.fire_and_water.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.fire_and_water.level.entities.Entity;
import com.oxology.fire_and_water.net.PlayerMP;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public List<Platform> platforms = new ArrayList<>();
    public List<Entity> entities = new ArrayList<>();

    public void addPlatform(float x, float y, float width, float height, Texture texture) {
        Platform platform = new Platform(x, y, width, height, texture);
        platforms.add(platform);
    }

    public void drawLevel(SpriteBatch batch) {
        for(Platform platform : platforms) {
            batch.draw(platform.texture, platform.x, platform.y, platform.width, platform.height);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removePlayer(String username) {
        entities.remove(getPlayer(username));
    }

    public PlayerMP getPlayer(String username) {
        for(Entity entity : entities) {
            if(entity instanceof PlayerMP) {
                if(((PlayerMP) entity).getUsername().equals(username))
                    return (PlayerMP) entity;
            }
        }
        return null;
    }
}
