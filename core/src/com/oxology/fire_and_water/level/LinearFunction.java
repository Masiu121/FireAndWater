package com.oxology.fire_and_water.level;

public class LinearFunction {
    float a, x, b;

    public LinearFunction(float a, float x, float b) {
        this.a = a;
        this.x = x;
        this.b = b;
    }

    public float calculate() {
        return a*x+b;
    }
}
