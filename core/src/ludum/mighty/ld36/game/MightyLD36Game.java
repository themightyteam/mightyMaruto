package ludum.mighty.ld36.game;

import ludum.mighty.ld36.screen.GameScreen;

import com.badlogic.gdx.Game;


public class MightyLD36Game extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		setScreen(new GameScreen(this));
	}

}
