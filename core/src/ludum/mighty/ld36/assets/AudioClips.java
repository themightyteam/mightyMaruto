package ludum.mighty.ld36.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by dchaves on 8/29/16.
 */
public class AudioClips {
    private static Sound arg;
    private static Sound choco;
    private static Sound explosion;
    private static Sound hadouken;
    private static Sound hit;
    private static Sound shift_hit;
    private static Sound powerup;
    private static Sound punch;
    private static Sound timeout;
    private static Sound wilhem;

    public AudioClips(){
        this.arg = Gdx.audio.newSound(Gdx.files.internal("sounds/arg.wav"));
        this.choco = Gdx.audio.newSound(Gdx.files.internal("sounds/choco.wav"));
        this.explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        this.hadouken = Gdx.audio.newSound(Gdx.files.internal("sounds/hadouken.wav"));
        this.hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit1.wav"));
        this.shift_hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit2.wav"));
        this.powerup = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup.wav"));
        this.punch = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.wav"));
        this.timeout = Gdx.audio.newSound(Gdx.files.internal("sounds/timeout.wav"));
        this.wilhem = Gdx.audio.newSound(Gdx.files.internal("sounds/wilhem.wav"));
    }

    public void play_arg() {
        AudioClips.arg.play();
    }

    public void play_choco() {
        AudioClips.choco.play();
    }

    public void play_explosion() {
        AudioClips.explosion.play();
    }

    public void play_hadouken() {
        AudioClips.hadouken.play();
    }

    public void play_hit() {
        AudioClips.hit.play();
    }

    public void play_shift_hit() {
        AudioClips.shift_hit.play();
    }

    public void play_powerup() {
        AudioClips.powerup.play();
    }

    public void play_punch() {
        AudioClips.punch.play();
    }

    public void play_timeout() {
        AudioClips.timeout.play();
    }

    public void play_wilhem() {
        AudioClips.wilhem.play();
    }
}
