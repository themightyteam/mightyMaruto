package ludum.mighty.ld36.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ludum.mighty.ld36.settings.DefaultValues;

public class AnimatorBoomerang {

	private Texture kidTexture;
	private TextureRegion[][] kidTR;
	public Animation animBoomerang, animStopBoomerang, anim;	
	
	public AnimatorBoomerang(String textureSheet) {
		kidTexture = new Texture(textureSheet);
		kidTR = TextureRegion.split(kidTexture, DefaultValues.TILESIZE, DefaultValues.TILESIZE);

		// Create animations for movement 
		animBoomerang  = new Animation(0.25f, kidTR[1][0], kidTR[1][1], kidTR[2][0], kidTR[2][1]);
		animStopBoomerang = new Animation(0.25f, kidTR[1][0]);



	
		// Set initial position of the kid
		anim = animStopBoomerang;
	}
	
	
	
	
}
