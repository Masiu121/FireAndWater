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
    boolean debug;

    public Player(FireAndWater main, String username, float x, float y) {
        super(main, UUID.nameUUIDFromBytes(("Player:" + username).getBytes()), x, y);
        this.username = username;
        this.debug = false;
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
            this.xSpeed = -this.defaultXSpeed;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            this.xSpeed = this.defaultXSpeed;
    }

    public void draw(SpriteBatch batch, BitmapFont font) {
        super.draw(batch);
        font.getData().setScale(2);
        font.draw(batch, username, this.x + FireAndWater.img.getWidth()/2 - usernameLength/2, this.y + FireAndWater.img.getHeight() + 50);
    }
}
