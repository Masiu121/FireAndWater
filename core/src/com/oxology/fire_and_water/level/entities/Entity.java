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
    public float x, y;
    int direction; //0 - right, 1 - left, 2 - up, 3 - down
    Texture texture;
    float xSpeed, ySpeed;
    float defaultXSpeed = 3.5f, defaultJumpSpeed = 8f;
    boolean jump, touchingGround;
    public Platform[] colliders;

    public Entity(FireAndWater main, float x, float y, Texture texture) {
        this.main = main;

        this.uuid = UUID.randomUUID();
        this.direction = 0;
        this.x = x;
        this.y = y;
        this.xSpeed = 0f;
        this.ySpeed = 0f;
        this.jump = false;
        this.texture = texture;
        colliders = new Platform[4];
    }

    public Entity(FireAndWater main, UUID uuid, float x, float y, Texture texture) {
        this.main = main;

        this.uuid = uuid;
        this.direction = 0;
        this.x = x;
        this.y = y;
        this.xSpeed = 0f;
        this.ySpeed = 0f;
        this.jump = true;
        this.texture = texture;
        colliders = new Platform[4];
    }

    public void move(float x, float y) {
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
        ySpeed = defaultJumpSpeed;
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
        colliders[0] = collidingTop(main.level.platforms);
        colliders[1] = collidingDown(main.level.platforms, ySpeed);
        colliders[2] = collidingRight(main.level.platforms);
        colliders[3] = collidingLeft(main.level.platforms);

        if(colliders[1] == null)
            jump = true;

        if(jump) {
            if(colliders[1] == null) {
                if (colliders[0] == null) {
                    ySpeed -= FireAndWater.gravity;
                    move(0, ySpeed);
                    touchingGround = false;
                }
                else {
                    ySpeed = 0;
                    ySpeed -= FireAndWater.gravity;
                    move(0, ySpeed);
                    this.y = colliders[0].y - texture.getHeight();
                    touchingGround = false;
                }
            }
            else {
                ySpeed = 0;
                this.y = colliders[1].y + colliders[1].height;
                touchingGround = true;
                jump = false;
            }
        }

        if(xSpeed > 0) {
            if(colliders[2] == null) {
                move(xSpeed, 0);
            }
            else {
                this.x = colliders[2].x - texture.getWidth();
            }
        }
        if(xSpeed < 0) {
            if(colliders[3] == null) {
                move(xSpeed, 0);
            }
            else {
                this.x = colliders[3].x + colliders[3].width;
            }
        }

        xSpeed = 0;
    }

    public Platform collidingTop(List<Platform> platforms) {
        for(Platform platform : platforms) {
            if(this.y + ySpeed + texture.getHeight() >= platform.y && this.y + ySpeed + texture.getHeight() <= platform.y + platform.height/2) //Check for y
                if(this.x + texture.getWidth() > platform.x && this.x < platform.x + platform.width) //Check for x
                    return platform;
        }
        return null;
    }

    public Platform collidingDown(List<Platform> platforms, float yOffset) {
        for(Platform platform : platforms) {
            if(this.y + yOffset >= platform.y + platform.height/2 && this.y + yOffset <= platform.y + platform.height) //Check for y
                if(this.x + texture.getWidth() > platform.x && this.x < platform.x + platform.width) //Check for x
                    return platform;
        }
        return null;
    }

    public Platform collidingRight(List<Platform> platforms) {
        for(Platform platform : platforms) {
            if(this.y + texture.getHeight() > platform.y && this.y < platform.y + platform.height) //Check for y
                if(this.x + xSpeed + texture.getWidth() >= platform.x && this.x + xSpeed + texture.getWidth() <= platform.x + platform.width/2) //Check for x
                    return platform;
        }
        return null;
    }

    public Platform collidingLeft(List<Platform> platforms) {
        for(Platform platform : platforms) {
            if(this.y + texture.getHeight() > platform.y && this.y < platform.y + platform.height) //Check for y
                if(this.x + xSpeed >= platform.x + platform.width/2 && this.x + xSpeed <= platform.x + platform.width) //Check for x
                    return platform;
        }
        return null;
    }
}
