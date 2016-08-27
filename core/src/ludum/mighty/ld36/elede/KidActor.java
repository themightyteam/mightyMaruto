package ludum.mighty.ld36.elede;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class KidActor extends Actor {

	private Texture kidTexture;
	private TextureRegion[][] kidTR;
	private TextureRegion[][] kidTRflip;
	public Animation animUP, animDOWN, animLEFT, animRIGHT,
	                 animPunchUP, animPunchDOWN, animPunchLEFT, animPunchRIGHT,
	                 animBlockUP, animBlockDOWN, animBlockLEFT, animBlockRIGHT,
	                 animBirds, anim;
	public int KID_WIDTH = 32;
	public int KID_HEIGHT = 32;

	private float elapsedTime = 0;

	private MoveByAction mba;
	private boolean moveFlag = false;

	public KidActor() {

		kidTexture = new Texture("maruto_spreadsheet.png");
		kidTR = TextureRegion.split(kidTexture, KID_WIDTH, KID_HEIGHT);
		kidTRflip = TextureRegion.split(kidTexture, KID_WIDTH, KID_HEIGHT);
        kidTRflip[0][3].flip(true, false);
        kidTRflip[0][4].flip(true, false);
        kidTRflip[0][5].flip(true, false);

        kidTRflip[2][3].flip(true, false);
        kidTRflip[2][4].flip(true, false);
        kidTRflip[2][5].flip(true, false);
        
        kidTRflip[1][2].flip(true, false);
        
		// Create animations for movement 
		animDOWN  = new Animation(0.25f, kidTR[0][0], kidTR[0][1], kidTR[0][2], kidTR[0][1]);
		animRIGHT  = new Animation(0.25f, kidTR[0][3], kidTR[0][4], kidTR[0][5], kidTR[0][4]);
		animUP    = new Animation(0.25f, kidTR[0][6], kidTR[0][7], kidTR[0][8], kidTR[0][7]);
		animLEFT = new Animation(0.25f, kidTRflip[0][3], kidTRflip[0][4], kidTRflip[0][5], kidTRflip[0][4]);

		animPunchDOWN = new Animation(0.25f, kidTR[0][1], kidTR[2][0], kidTR[2][1], kidTR[2][2]);
		animPunchRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[2][3], kidTR[2][4], kidTR[2][5]);
		animPunchUP = new Animation(0.25f, kidTR[0][7], kidTR[2][6], kidTR[2][7], kidTR[2][8]);
		animPunchLEFT = new Animation(0.25f, kidTRflip[0][5], kidTRflip[2][3], kidTRflip[2][4], kidTRflip[2][5]);		
		
		animBlockDOWN = new Animation(0.25f, kidTR[0][1], kidTR[1][1]);
		animBlockRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[1][2]);
		animBlockUP = new Animation(0.25f, kidTR[0][7], kidTR[1][0]);
		animBlockLEFT = new Animation(0.25f, kidTRflip[0][4], kidTRflip[1][2]);
		
		
		animBirds = new Animation(0.25f, kidTRflip[3][0], kidTRflip[3][1], kidTRflip[3][2], kidTRflip[3][3]);
		// Set initial position of the kid
		anim = animDOWN;
		setPosition(0, 0);
		//anim.getKeyFrame(0, movement);

		setBounds(getX(), getY(), KID_WIDTH, KID_HEIGHT);


	}

	@Override
	public void draw(Batch batch, float alpha) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(anim.getKeyFrame(elapsedTime, moveFlag), getX(), getY());
//		batch.draw(anim.getKeyFrame(elapsedTime, true), getX(), getY());

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (this.getActions().size == 0) {
			moveFlag = false;
		}
	}

	public void checkMovement() {

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if (moveFlag == false) {
				anim = animLEFT;
				//if (Math.round(getX()) > 0) {
					mba = new MoveByAction();
					mba.setAmount(-32,0);
					mba.setDuration(1f);
					this.addAction(mba);
				//}
				moveFlag = true;
			}
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			if (moveFlag == false) {
				anim = animRIGHT;
				//        	if (getX() < 1440 - KID_WIDTH) 
				//        		moveBy(2, 0);
				//        	movement = true;
				//if (Math.round(getX()) < 1440 - KID_WIDTH)	{
					mba = new MoveByAction();
					mba.setAmount(32,0);
					mba.setDuration(1f);
					this.addAction(mba);
				//}
				moveFlag = true;
			}


		}


		if(Gdx.input.isKeyPressed(Input.Keys.UP)){

			if (moveFlag == false) {
				anim = animUP;
				//if (Math.round(getY()) < 1440 - KID_WIDTH) {        		
					mba = new MoveByAction();
					mba.setAmount(0,32);
					mba.setDuration(1f);
					this.addAction(mba);
				//}
				moveFlag = true;

			}
		}


		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){

			if (moveFlag == false) {
				anim = animDOWN;
				//if (Math.round(getY()) > 0) {
					mba = new MoveByAction();
					mba.setAmount(0,-32);
					mba.setDuration(1f);
					this.addAction(mba);
				//}

				moveFlag = true;

			}	
		}
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.P)) {
			if (moveFlag == false) {
			if (anim == animDOWN) {
				anim = animPunchDOWN;
			} else if (anim == animUP) {
				anim = animPunchUP;
			} else if (anim == animRIGHT) {
				anim = animPunchRIGHT;
			} else if (anim == animLEFT) {
				anim = animPunchLEFT;
			}
			mba = new MoveByAction();
			mba.setAmount(0, 0);
			mba.setDuration(1f);
			this.addAction(mba);
			
			moveFlag = true;
			}
		}

		if(Gdx.input.isKeyPressed(Input.Keys.B)) {
			if (moveFlag == false) {
			if (anim == animDOWN) {
				anim = animBlockDOWN;
			} else if (anim == animUP) {
				anim = animBlockUP;
			} else if (anim == animRIGHT) {
				anim = animBlockRIGHT;
			} else if (anim == animLEFT) {
				anim = animBlockLEFT;
			}
			mba = new MoveByAction();
			mba.setAmount(0, 0);
			mba.setDuration(1f);
			this.addAction(mba);
			
			moveFlag = true;
			}
		}		
		
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.O)) {
			if (moveFlag == false) {
			anim=animBirds;
			mba = new MoveByAction();
			mba.setAmount(0, 0);
			mba.setDuration(5f);
			this.addAction(mba);
			
			moveFlag = true;
			}
		}
		
		
//		if(Gdx.input.isKeyPressed(Input.Keys.V)){
//
//			if (moveFlag == false) {
//				AlphaAction aa = new AlphaAction();
//				
//				moveFlag = true;
//
//			}	
//		}		
	}

}
