package com.oxology.fire_and_water;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
	
	@Override
	public void create () {
		level = new Level();
		level.addPlatform(100f, 300f, 300, 50);
		level.addPlatform(300f, 290f, 200, 50);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		System.out.print("Do you want to run the server? (y/n): ");
		char choice = scanner.next().charAt(0);
		if(choice == 'y') {
			server = new GameServer(this);
			server.start();
		}

		client = new GameClient(this, "localhost");
		client.start();
		client.sendData("ping".getBytes());

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
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		player.draw(batch, font);
		batch.draw(img, level.platforms.get(0).x, level.platforms.get(0).y, level.platforms.get(0).width, level.platforms.get(0).height);
		batch.draw(img, level.platforms.get(1).x, level.platforms.get(1).y, level.platforms.get(1).width, level.platforms.get(1).height);
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
}
