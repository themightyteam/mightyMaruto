package ludum.mighty.ld36.game;

import ludum.mighty.ld36.assets.SoundAssets;
import ludum.mighty.ld36.screen.IntroScreen;

import com.badlogic.gdx.Game;


public class MightyLD36Game extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		setScreen(new IntroScreen(this, new SoundAssets()));
	}

}
