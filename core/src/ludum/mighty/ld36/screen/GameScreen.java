package ludum.mighty.ld36.screen;

import ludum.mighty.ld36.world.MightyWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen extends DefaultScreen implements Screen {

	private TiledMap map;
	MightyWorld gameWorld;
	
	int waitFramesForHandle = 100;

	public GameScreen(Game game) {
		super(game);

		this.map = new TmxMapLoader().load("maps/tatami.tmx");

		this.gameWorld = new MightyWorld();
		this.gameWorld.init(this.map);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		this.waitFramesForHandle -= 1;
//		if (this.waitFramesForHandle <= 0)
//			handleInput();

		this.gameWorld.update();
		this.gameWorld.render();
	}

	@Override
	public void resize(int width, int height) {
		this.gameWorld.sv.update(width, height);
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			//this.mightyGame.setScreen(new ScoresScreen(this.mightyGame));
		}
	}

	
	
}
