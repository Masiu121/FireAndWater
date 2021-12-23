package com.oxology.fire_and_water;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.fire_and_water.net.GameClient;
import com.oxology.fire_and_water.net.GameServer;

import java.util.Scanner;

public class FireAndWater extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	GameServer server;
	GameClient client;
	Scanner scanner = new Scanner(System.in);
	
	@Override
	public void create () {
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
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
