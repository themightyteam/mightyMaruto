package ludum.mighty.ld36.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ludum.mighty.ld36.game.MightyLD36Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mighty LD36";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new MightyLD36Game(), config);
	}
}
