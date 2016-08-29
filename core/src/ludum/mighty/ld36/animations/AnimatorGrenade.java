package ludum.mighty.ld36.animations;

import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatorGrenade {

	private Texture kidTexture;
	private TextureRegion[][] kidTR;
	public Animation animStopGrenade, anim;

	public AnimatorGrenade(String textureSheet) {
		kidTexture = new Texture(textureSheet);
		kidTR = TextureRegion.split(kidTexture, DefaultValues.TILESIZE,
				DefaultValues.TILESIZE);

		// Create animations for movement
		animStopGrenade = new Animation(0.25f, kidTR[1][3]);

		// Set initial position of the kid
		anim = animStopGrenade;
	}

}
