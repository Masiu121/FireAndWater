package com.oxology.fire_and_water;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.fire_and_water.level.Level;
import com.oxology.fire_and_water.level.entities.Player;
import com.oxology.fire_and_water.net.GameClient;
import com.oxology.fire_and_water.net.GameServer;

import java.util.Scanner;

public class FireAndWater extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	GameServer server;
	GameClient client;
	Scanner scanner = new Scanner(System.in);
	Player player;
	public static float gravity;
	BitmapFont font;
	GlyphLayout layout;
	public Level level;
	boolean showInfo;
	
	@Override
	public void create () {
		showInfo = false;
		img = new Texture("badlogic.jpg");

		level = new Level();
		//floor
		level.addPlatform(0f, -50f, 1920f, 100f, img);

		//stairs
		level.addPlatform(1000f, 100f, 400f, 50f, img);
		level.addPlatform(1350f, 200f, 400f, 50f, img);
		level.addPlatform(1700f, 300f, 400f, 50f, img);
		level.addPlatform(1750f, 400f, 400f, 50f, img);
		level.addPlatform(1800f, 500f, 400f, 50f, img);

		//platforms
		level.addPlatform(900f, 550f, 500f, 50f, img);
		level.addPlatform(200f, 350f, 500f, 50f, img);

		batch = new SpriteBatch();

		/*
		System.out.print("Do you want to run the server? (y/n): ");
		char choice = scanner.next().charAt(0);
		if(choice == 'y') {
			server = new GameServer(this);
			server.start();
		}

		client = new GameClient(this, "localhost");
		client.start();
		client.sendData("ping".getBytes());
		*/

		font = new BitmapFont(Gdx.files.internal("PixelFont.fnt"));
		font.getData().scale(0.5f);

		layout = new GlyphLayout();

		gravity = 0.25f;
		player = new Player(this, "Maksuu121", img);
		player.setUsernameLength();
	}

	public float getStringWidth(String string) {
		layout.setText(font, string);
		return layout.width;
	}

	@Override
	public void render () {
		update();
		ScreenUtils.clear(0, 0, 1, 1);
		if(Gdx.input.isKeyJustPressed(Input.Keys.F3))
			toggleDebug();
		batch.begin();
		player.draw(batch, font);
		level.drawLevel(batch);
		if(showInfo) {
			int lineHeight = 20;
			int lineOffset = -5;
			font.setColor(Color.WHITE);
			font.getData().setScale(0.4f);

			font.draw(batch, "Collision TOP: ", 2, Gdx.graphics.getHeight() - lineHeight * 0 + lineOffset);
			font.draw(batch, "Collision DOWN: ", 2, Gdx.graphics.getHeight() - lineHeight * 1 + lineOffset);
			font.draw(batch, "Collision RIGHT: ", 2, Gdx.graphics.getHeight() - lineHeight * 2 + lineOffset);
			font.draw(batch, "Collision LEFT: ", 2, Gdx.graphics.getHeight() - lineHeight * 3 + lineOffset);
			font.draw(batch, "XY: " + player.x + ", " + player.y, 2, Gdx.graphics.getHeight() - lineHeight * 4 + lineOffset);

			for (int i = 0; i < 4; i++) {
				if (player.colliders[i] != null) {
					font.draw(batch, "True", 220, Gdx.graphics.getHeight() - lineHeight * (i) + lineOffset);
				} else {
					font.draw(batch, "False", 220, Gdx.graphics.getHeight() - lineHeight * (i) + lineOffset);
				}
			}
		}
		batch.end();
	}

	public void update() {
		player.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void toggleDebug() {
		showInfo = !showInfo;
	}
}
