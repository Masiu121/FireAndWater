package com.oxology.fire_and_water.level.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.fire_and_water.FireAndWater;

import java.util.UUID;

public class Player extends Entity {
    String username;
    float usernameLength;

    public Player(FireAndWater main, String username, Texture texture) {
        super(main, UUID.nameUUIDFromBytes(("Player:" + username).getBytes()), 20, 0, texture);
        this.username = username;
    }

    public void setUsernameLength() {
        this.usernameLength = main.getStringWidth(this.username);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void update() {
        super.update();
        if(Gdx.input.isKeyPressed(Input.Keys.W) && touchingGround)
            jump();
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            move(-xSpeed, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            move(xSpeed, 0);
    }

    public void draw(SpriteBatch batch, BitmapFont font) {
        super.draw(batch);
        font.draw(batch, username, this.x + texture.getWidth()/2 - usernameLength/2, this.y + texture.getHeight() + 50);
    }
}
