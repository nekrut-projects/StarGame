package ru.gb.stargame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.gb.stargame.StarGame;
import ru.gb.stargame.screen.ScreenManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ScreenManager.SCREEN_WIDTH;
		config.height = ScreenManager.SCREEN_HEIGHT;
		new LwjglApplication(new StarGame(), config);
	}
}
