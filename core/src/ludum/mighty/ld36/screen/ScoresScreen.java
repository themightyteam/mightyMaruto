package ludum.mighty.ld36.screen;

import java.util.ArrayList;

import ludum.mighty.ld36.assets.SoundAssets;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.utils.ScoreUtils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ScoresScreen extends DefaultScreen implements Screen {

	SpriteBatch batch;
	Texture img;
	Sprite spr;
	OrthographicCamera cam;
	StretchViewport sv;

	int waitFramesForHandle = DefaultValues.WAIT_TIME;

	ArrayList<ScoreUtils> scoreList;

	public ScoresScreen(Game game, SoundAssets sa,
			ArrayList<ScoreUtils> scoreList) {
		super(game, sa);

		this.scoreList = scoreList;

		this.cam = new OrthographicCamera();
		this.sv = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.cam);
		this.cam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);

		this.batch = new SpriteBatch();
		this.img = new Texture("scores.png");
		this.spr = new Sprite(this.img);
		this.spr.setPosition(0, 0);
		this.spr.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
	}

	private void handleInput() {



		if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			this.mightyGame.setScreen(new IntroScreen(this.mightyGame,
					this.soundAssets));
		}
	}	
	
}
