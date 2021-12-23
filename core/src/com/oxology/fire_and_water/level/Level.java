package com.oxology.fire_and_water.level;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public List<Platform> platforms = new ArrayList<>();

    public void addPlatform(float x, float y, float width, float height) {
        Platform platform = new Platform(x, y, width, height);
        platforms.add(platform);
    }
}
