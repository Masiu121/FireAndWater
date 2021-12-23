package com.oxology.fire_and_water.level.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.fire_and_water.FireAndWater;
import com.oxology.fire_and_water.level.Platform;

import java.util.List;
import java.util.UUID;

public class Entity {
    FireAndWater main;

    UUID uuid;
    float x, y;
    int direction; //0 - right, 1 - left, 2 - up, 3 - down
    Texture texture;
    float xSpeed, ySpeed;
    boolean jump, touchingGround;

    public Entity(FireAndWater main, float x, float y, Texture texture) {
        this.main = main;

        this.uuid = UUID.randomUUID();
        this.direction = 0;
        this.x = x;
        this.y = y;
        this.xSpeed = 3.5f;
        this.ySpeed = 0f;
        this.jump = false;
        this.texture = texture;
    }

    public Entity(FireAndWater main, UUID uuid, float x, float y, Texture texture) {
        this.main = main;

        this.uuid = uuid;
        this.direction = 0;
        this.x = x;
        this.y = y;
        this.xSpeed = 3.5f;
        this.ySpeed = 0f;
        this.jump = false;
        this.texture = texture;
    }

    public void move(float x, float y) {
        if(x != 0) {
            Platform rightCollider = collidingRight(main.level.platforms);
            Platform leftCollider = collidingLeft(main.level.platforms);

            if(rightCollider != null) {
                this.x = rightCollider.x - texture.getWidth();

                return;
            }

            if(leftCollider != null) {
                this.x = leftCollider.x + leftCollider.width;

                return;
            }
        }

        if(this.x > x)
            direction = 1;
        else
            direction = 0;

        if(this.y > y)
            direction = 3;
        else
            direction = 2;

        this.x = this.x + x;
        this.y = this.y + y;
    }

    public void jump() {
        ySpeed = 6f;
        jump = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, this.x, this.y);
    }

    public void update() {
        if(y != 0)
            touchingGround = false;
        else
            touchingGround = true;

        if(jump) {
            Platform collidingPlatform = collidingTop(main.level.platforms);
            if(collidingPlatform == null) {
                move(0, ySpeed);
                ySpeed -= FireAndWater.gravity;
                if (this.y < 0) {
                    ySpeed = 0;
                    this.y = 0;
                }
            }
            else {
                ySpeed = 0;
                this.y = collidingPlatform.y - texture.getHeight();
            }
        }
    }

    public Platform collidingTop(List<Platform> platforms) {
        for(Platform platform : platforms) {
            if(this.y + ySpeed + texture.getHeight() > platform.y && this.y + ySpeed < platform.y + platform.height) //Check for y
                if(this.x + texture.getWidth() > platform.x && this.x < platform.x + platform.width) //Check for x
                    return platform;
        }
        return null;
    }

    public Platform collidingRight(List<Platform> platforms) {
        for(Platform platform : platforms) {
            if(this.y + texture.getHeight() > platform.y && this.y < platform.y + platform.height) //Check for y
                if(this.x + xSpeed + texture.getWidth() >= platform.x && this.x + xSpeed <= platform.x + platform.width) //Check for x
                    return platform;
        }
        return null;
    }

    public Platform collidingLeft(List<Platform> platforms) {
        for(Platform platform : platforms) {
            if(this.y + texture.getHeight() > platform.y && this.y < platform.y + platform.height) //Check for y
                if(this.x - xSpeed + texture.getWidth() >= platform.x && this.x - xSpeed <= platform.x + platform.width) //Check for x
                    return platform;
        }
        return null;
    }

    public Platform collidingDown(List<Platform> platforms) {
        for(Platform platform : platforms) {
            if(this.y - ySpeed + texture.getHeight() > platform.y && this.y - ySpeed < platform.y + platform.height) //Check for y
                if(this.x + texture.getWidth() > platform.x && this.x < platform.x + platform.width) //Check for x
                    return platform;
        }
        return null;
    }
}
