package ludum.mighty.ld36.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundAssets {

	Music themeSong;
	Music themeSongSlow;
	Music themeSongFast;

	Sound hastalavista;

	public SoundAssets() {

		this.themeSongSlow = Gdx.audio.newMusic(Gdx.files
				.internal("sounds/MightyLD36_slow_1.mp3"));
		this.themeSong = Gdx.audio.newMusic(Gdx.files
				.internal("sounds/MightyLD36_normal_1.mp3"));
		this.themeSongFast = Gdx.audio.newMusic(Gdx.files
				.internal("sounds/MightyLD36_fast_1.mp3"));

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

	public void playThemeSlow() {
		this.themeSongSlow.play();
		this.themeSongSlow.setLooping(true);
	}

	public void stopThemeSlow() {
		this.themeSongSlow.stop();
		this.themeSongSlow.setLooping(true);
	}

	public void playThemeFast() {
		this.themeSongFast.play();
		this.themeSongFast.setLooping(true);
	}

	public void stopThemeFast() {
		this.themeSongFast.stop();
		this.themeSongFast.setLooping(true);
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
