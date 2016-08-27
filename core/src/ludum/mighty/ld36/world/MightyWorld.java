package ludum.mighty.ld36.world;

import ludum.mighty.ld36.actors.BasicMaruto;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class MightyWorld {

	// The world can be in two states:
	// 1. Entering a command from the console (nobody moves)
	// 2. Actors perform this turn's actions (first the AI is updated, then the
	// mechanics of the world are calculated and translated into actors
	// actions and last the actions of the turn are displayed)
	private int currentState;

	private Stage stage;

	// Rendering objects
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera cam;
    public StretchViewport sv;
	
    public BasicMaruto basicMaruto;
    
	// // WORLD API
	// Loads stuff like the map and initializes things
	public void init(TiledMap map) {

		
		// Render init
		this.mapRenderer = new OrthogonalTiledMapRenderer(map);
		this.cam = new OrthographicCamera();
		this.sv = new StretchViewport(640, 480, this.cam);
		this.sv.apply();
		this.cam.position.set(640, 480, 0);

		
		stage = new Stage(sv);

		// TODO: define user input here

		this.currentState = DefaultValues.WORLD_STATE_ENTERING_COMMAND;
		initPlayers();
		updatePowerUps();
	}

	// updates the world (this depends on, among other things, the current
	// state of the world)
	// this is called in the screen render or update
	public void update() {
		if (this.currentState == DefaultValues.WORLD_STATE_ENTERING_COMMAND) {
			// TODO: update user input here

		} else if (this.currentState == DefaultValues.WORLD_STATE_ACTION) {

		}

		//this.stage.act(Gdx.graphics.getDeltaTime());
	}

	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updatePowerUps();
		
		this.cam.position.x = basicMaruto.getX();
		this.cam.position.y = basicMaruto.getY();
		this.cam.update();
		this.mapRenderer.setView(this.cam);

		this.mapRenderer.render();
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
	}

	// // END OF WORLD API

	// Creates all players
	private void initPlayers() {
		// Add playable player
		basicMaruto = new BasicMaruto();
		// set the xy for the tiles and stage position
		basicMaruto.setTilePosX(20);
		basicMaruto.setTilePosY(20);
		this.stage.addActor(basicMaruto);

		// TODO: Add rest of players
	}

	// Checks all present power up items on the map and spawns new power ups if
	// necessary
	private void updatePowerUps() {

	}
}
