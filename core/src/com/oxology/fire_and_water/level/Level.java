package com.oxology.fire_and_water.level;

import java.util.ArrayList;
import java.util.List;

public class Level {
    List<Platform> platforms = new ArrayList<>();

    public void addPlatform(float xOffset, float yOffset) {
        Platform platform = new Platform(xOffset, yOffset);
        platforms.add(platform);
    }
}
