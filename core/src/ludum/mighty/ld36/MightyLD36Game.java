package ludum.mighty.ld36;

import com.badlogic.gdx.Game;

import ludum.mighty.screen.IntroScreen;


public class MightyLD36Game extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		setScreen(new IntroScreen(this));
	}

}
