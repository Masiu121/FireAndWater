package com.oxology.fire_and_water.level;

import java.util.List;

public class Platform {
    float xOffset, yOffset;
    List<LinearFunction> sides;

    public Platform(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void addSide(int a, int x, int b) {
        LinearFunction function = new LinearFunction(a, x, b);
        sides.add(function);
    }
}
