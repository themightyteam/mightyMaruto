package ludum.mighty.ld36.game;

import com.badlogic.gdx.Game;

import ludum.mighty.ld36.screen.IntroScreen;


public class MightyLD36Game extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		setScreen(new IntroScreen(this));
	}

}
