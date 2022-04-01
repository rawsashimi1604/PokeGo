package com.pokego.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pokego.PokeGo;

/**
 * DesktopLauncher to launch PokeGo
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//huk
		// Change config settings
		config.width = PokeGo.WIDTH;
		config.height = PokeGo.HEIGHT;
		config.title = PokeGo.TITLE;

		new LwjglApplication(new PokeGo(), config);
	}
}
