package ludum.mighty.ld36.screen;

import ludum.mighty.ld36.assets.SoundAssets;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.world.MightyWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameScreen extends DefaultScreen implements Screen {

	private TiledMap map;
	MightyWorld gameWorld;
	
	int waitFramesForHandle = DefaultValues.WAIT_TIME;

	public GameScreen(Game game, SoundAssets sa) {
		super(game, sa);

		this.map = new TmxMapLoader().load("maps/tatami.tmx");

		this.gameWorld = new MightyWorld();
		this.gameWorld.init(this.map);

		this.soundAssets.playTheme();
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		this.gameWorld.update();
		this.gameWorld.render();
	}

	@Override
	public void resize(int width, int height) {
		this.gameWorld.sv.update(width, height);
	}


}
