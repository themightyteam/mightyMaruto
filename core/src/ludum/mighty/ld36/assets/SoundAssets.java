package ludum.mighty.ld36.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundAssets {

	Music themeSong;

	Sound hastalavista;

	public SoundAssets() {

		this.themeSong = Gdx.audio.newMusic(Gdx.files
				.internal("sounds/theme_1.mp3"));

		this.hastalavista = Gdx.audio.newSound(Gdx.files
				.internal("sounds/hasta_la_vista.wav"));
	}

	public void playTheme() {
		this.themeSong.play();
		this.themeSong.setLooping(true);
	}

	public void stopTheme() {
		this.themeSong.stop();
		this.themeSong.setLooping(true);
	}

	public void playHastalavista() {
		this.hastalavista.play();
	}

	public void disposeObjects() {
		this.themeSong.stop();
		this.themeSong.dispose();

		this.hastalavista.dispose();
	}
}
