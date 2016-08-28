package ludum.mighty.ld36.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ludum.mighty.ld36.settings.DefaultValues;

public class AnimatorMaruto {

	private Texture kidTexture;
	private TextureRegion[][] kidTR;
	private TextureRegion[][] kidTRflip;
	public Animation animUP, animDOWN, animLEFT, animRIGHT,
	animPunchUP, animPunchDOWN, animPunchLEFT, animPunchRIGHT,
	animBlockUP, animBlockDOWN, animBlockLEFT, animBlockRIGHT,
	animHitUP, animHitDOWN, animHitLEFT, animHitRIGHT,	
	animStopUP, animStopDOWN, animStopLEFT, animStopRIGHT,
	animBirds, animDeath, anim;
	
	
	public AnimatorMaruto(String textureSheet) {
		kidTexture = new Texture(textureSheet);
		kidTR = TextureRegion.split(kidTexture, DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		kidTRflip = TextureRegion.split(kidTexture, DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		kidTRflip[0][3].flip(true, false);
		kidTRflip[0][4].flip(true, false);
		kidTRflip[0][5].flip(true, false);

		kidTRflip[2][3].flip(true, false);
		kidTRflip[2][4].flip(true, false);
		kidTRflip[2][5].flip(true, false);

		kidTRflip[1][2].flip(true, false);
		kidTRflip[1][7].flip(true, false);
		kidTRflip[1][9].flip(true, false);
		
		
		

		// Create animations for movement 
		animDOWN  = new Animation(0.25f, kidTR[0][0], kidTR[0][1], kidTR[0][2], kidTR[0][1]);
		animRIGHT  = new Animation(0.25f, kidTR[0][3], kidTR[0][4], kidTR[0][5], kidTR[0][4]);
		animUP    = new Animation(0.25f, kidTR[0][6], kidTR[0][7], kidTR[0][8], kidTR[0][7]);
		animLEFT = new Animation(0.25f, kidTRflip[0][3], kidTRflip[0][4], kidTRflip[0][5], kidTRflip[0][4]);

		animPunchDOWN = new Animation(0.25f, kidTR[0][1], kidTR[2][0], kidTR[2][1], kidTR[2][2]);
		animPunchRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[2][3], kidTR[2][4], kidTR[2][5]);
		animPunchUP = new Animation(0.25f, kidTR[0][7], kidTR[2][6], kidTR[2][7], kidTR[2][8]);
		animPunchLEFT = new Animation(0.25f, kidTRflip[0][4], kidTRflip[2][3], kidTRflip[2][4], kidTRflip[2][5]);		

		animBlockDOWN = new Animation(0.25f, kidTR[0][1], kidTR[1][1]);
		animBlockRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[1][2]);
		animBlockUP = new Animation(0.25f, kidTR[0][7], kidTR[1][0]);
		animBlockLEFT = new Animation(0.25f, kidTRflip[0][4], kidTRflip[1][2]);

		animStopDOWN = new Animation(0.25f, kidTR[0][1], kidTR[2][9]);
		animStopRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[1][9]);
		animStopUP = new Animation(0.25f, kidTR[0][7], kidTR[0][9]);
		animStopLEFT = new Animation(0.25f, kidTRflip[0][4], kidTRflip[1][9]);

		animHitDOWN = new Animation(0.25f, kidTR[0][1], kidTR[1][5], kidTR[1][6], kidTR[0][1]);
		animHitRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[1][7], kidTR[1][8], kidTR[0][4]);
		animHitUP = new Animation(0.25f, kidTR[0][7], kidTR[1][3], kidTR[1][4], kidTR[0][7]);
		animHitLEFT = new Animation(0.25f, kidTRflip[0][4], kidTRflip[1][7], kidTRflip[1][8], kidTRflip[0][4]);		

	

		animBirds = new Animation(0.25f, kidTRflip[3][0], kidTRflip[3][1], kidTRflip[3][2], kidTRflip[3][3]);
		animDeath = new Animation(0.25f, kidTR[3][4]);
		
		
		// Set initial position of the kid
		anim = animDOWN;
	}
	
	
	
	
}
