package ludum.mighty.ld36.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ludum.mighty.ld36.settings.DefaultValues;

public class AnimatorArrrggghhh {

	private Texture kidTexture;
	private TextureRegion[][] kidTR;
	private TextureRegion[][] kidTRflip;
	public Animation animUP, animDOWN, animLEFT, animRIGHT,	
	animStopUP, animStopDOWN, animStopLEFT, animStopRIGHT,
	anim;
	
	
	public AnimatorArrrggghhh(String textureSheet) {
		kidTexture = new Texture(textureSheet);
		kidTR = TextureRegion.split(kidTexture, DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		kidTRflip = TextureRegion.split(kidTexture, DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		kidTRflip[0][4].flip(true, false);
		kidTRflip[0][5].flip(true, false);
		kidTRflip[0][6].flip(true, false);
		

		// Create animations for movement 
		animDOWN  = new Animation(0.25f, kidTR[1][4], kidTR[1][5], kidTR[1][6], kidTR[1][4]);
		animRIGHT  = new Animation(0.25f, kidTR[0][4], kidTR[0][5], kidTR[0][6], kidTR[0][4]);
		animUP    = new Animation(0.25f, kidTR[2][4], kidTR[2][5], kidTR[2][6], kidTR[2][4]);
		animLEFT = new Animation(0.25f, kidTRflip[0][4], kidTRflip[0][5], kidTRflip[0][6], kidTRflip[0][4]);

		animStopDOWN = new Animation(0.25f, kidTR[1][4]);
		animStopRIGHT = new Animation(0.25f, kidTR[0][4]);
		animStopUP = new Animation(0.25f, kidTR[2][4]);
		animStopLEFT = new Animation(0.25f, kidTRflip[0][4]);


	
		// Set initial position of the kid
		anim = animStopDOWN;
	}
	
	
	
	
}
