package ludum.mighty.ld36.client;

import ludum.mighty.ld36.game.MightyLD36Game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(640, 480);
        }

        @Override
        public ApplicationListener createApplicationListener () {
		return new MightyLD36Game();
        }
}