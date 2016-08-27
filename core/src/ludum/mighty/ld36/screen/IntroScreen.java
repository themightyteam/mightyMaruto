package ludum.mighty.ld36.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.badlogic.gdx.Screen;

public class IntroScreen extends DefaultScreen implements Screen {

	SpriteBatch batch;
	Texture img;
	Sprite spr;
	OrthographicCamera cam;
	StretchViewport sv;

	int waitFramesForHandle = 100;

	public IntroScreen(Game game) {
		super(game);

		this.cam = new OrthographicCamera();
		this.sv = new StretchViewport(100, 100, this.cam);
		this.sv.apply();
		this.cam.position.set(50, 50, 0);

		this.batch = new SpriteBatch();
		this.img = new Texture("intro.png");
		this.spr = new Sprite(this.img);
		this.spr.setPosition(0, 0);
		this.spr.setSize(100, 100);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.waitFramesForHandle -= 1;
		if (this.waitFramesForHandle <= 0)
			handleInput();

		batch.setProjectionMatrix(this.cam.combined);

		this.batch.begin();
		this.spr.draw(batch);
		this.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		this.sv.update(width, height);
		//this.cam.position.set(this.cam.viewportWidth / 2, this.cam.viewportHeight / 2, 0);
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			this.mightyGame.setScreen(new HistoryScreen(this.mightyGame));
		}
	}	
	
}
