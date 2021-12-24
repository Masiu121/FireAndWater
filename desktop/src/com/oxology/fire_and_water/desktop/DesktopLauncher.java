package com.oxology.fire_and_water.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oxology.fire_and_water.FireAndWater;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		config.resizable = false;
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new FireAndWater(), config);
	}
}
