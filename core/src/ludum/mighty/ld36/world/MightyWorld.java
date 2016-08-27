package ludum.mighty.ld36.world;

import com.badlogic.gdx.maps.tiled.TiledMap;


public class MightyWorld {

	// The world can be in two states:
	// 1. Entering a command from the console (nobody moves)
	// 2. Actors perform this turn's actions (first the AI is updated, then the
	// mechanics of the world are calculated and translated into actors
	// actions and last the actions of the turn are displayed)

	// // WORLD API
	// Loads stuff like the map and initializes things
	public void init(TiledMap map) {

		// TODO: define user input here

	}

	// updates the world (this depends on, among other things, the current
	// state of the world)
	// this is called in the screen render or update
	public void update() {

		// TODO: update user input here

	}

	public void render() {

	}

	// // END OF WORLD API

	// Creates all players
	private void initPlayers() {

	}

	// Checks all present power up items on the map and spawns new power ups if
	// necessary
	private void updatePowerUps() {

	}
}
