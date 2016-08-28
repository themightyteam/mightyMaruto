package ludum.mighty.ld36.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ludum.mighty.ld36.settings.DefaultValues;

public class AnimatorSonicBoom {

	private Texture kidTexture;
	private TextureRegion[][] kidTR;
	private TextureRegion[][] kidTRflip;
	public Animation animUP, animDOWN, animLEFT, animRIGHT,	
	animStopUP, animStopDOWN, animStopLEFT, animStopRIGHT,
	animNE, animNW, animSE, animSW,
	animStopNE, animStopNW, animStopSE, animStopSW,
	anim;
	
	
	public AnimatorSonicBoom(String textureSheet) {
		kidTexture = new Texture(textureSheet);
		kidTR = TextureRegion.split(kidTexture, DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		kidTRflip = TextureRegion.split(kidTexture, DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		
		kidTRflip[2][7].flip(true, false);
		kidTRflip[2][8].flip(true, false);
		kidTRflip[2][9].flip(true, false);

		kidTRflip[3][7].flip(true, false);
		kidTRflip[3][8].flip(true, false);
		kidTRflip[3][9].flip(true, false);

		kidTRflip[4][7].flip(true, false);
		kidTRflip[4][8].flip(true, false);
		kidTRflip[4][9].flip(true, false);

		
		

		// Create animations for movement 
		animDOWN  = new Animation(0.25f, kidTR[5][7], kidTR[5][8], kidTR[5][9], kidTR[5][7]);
		animRIGHT  = new Animation(0.25f, kidTR[4][7], kidTR[4][8], kidTR[4][9], kidTR[4][7]);
		animUP  = new Animation(0.25f, kidTR[1][7], kidTR[1][8], kidTR[1][9], kidTR[1][7]);
		animLEFT  = new Animation(0.25f, kidTRflip[4][7], kidTRflip[4][8], kidTRflip[4][9], kidTRflip[4][7]);
		animNE  = new Animation(0.25f, kidTR[2][7], kidTR[2][8], kidTR[2][9], kidTR[2][7]);
		animNW  = new Animation(0.25f, kidTRflip[2][7], kidTRflip[2][8], kidTRflip[2][9], kidTRflip[2][7]);
		animSE  = new Animation(0.25f, kidTR[3][7], kidTR[3][8], kidTR[3][9], kidTR[3][7]);
		animSW  = new Animation(0.25f, kidTRflip[3][7], kidTRflip[3][8], kidTRflip[3][9], kidTRflip[3][7]);
		
		
		
		
		animStopDOWN = new Animation(0.25f, kidTR[5][7]);
		animStopRIGHT = new Animation(0.25f, kidTR[4][7]);
		animStopUP = new Animation(0.25f, kidTR[1][7]);
		animStopLEFT = new Animation(0.25f, kidTRflip[4][7]);
		animStopNE = new Animation(0.25f, kidTR[2][7]);
		animStopNW = new Animation(0.25f, kidTRflip[2][7]);
		animStopSE = new Animation(0.25f, kidTR[3][7]);
		animStopSW = new Animation(0.25f, kidTRflip[3][7]);


	
		// Set initial position of the kid
		anim = animStopDOWN;
	}
	
	
	
	
}
