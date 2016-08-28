package ludum.mighty.ld36.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorArrrggghhh;
import ludum.mighty.ld36.animations.AnimatorSonicBoom;
import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_SonicBoom extends Actor_Powerup 
{
	
	private AnimatorSonicBoom animator;
	private Animation anim;
	
	public Item_SonicBoom()
	{
		this.turnsLife = DefaultValues.ARRRGGGHHH_TURNS_LIFE;
		this.life = Integer.MAX_VALUE;
		this.shiftProbability = (float) DefaultValues.ARRRGGGHHH_SHIFT_PROB;
		this.punch = DefaultValues.ARRRGGGHHH_DAMAGE;
		this.speed = DefaultValues.ARRRGGGHHH_SPEED;
		
		this.animator = new AnimatorSonicBoom("items_spreadsheet.png");
		anim = animator.anim;
	}

	
private void doMovementWalk() {
		
		MoveByAction mba = new MoveByAction();
		
		switch (facing) {

		case NORTH:

			anim = animator.animUP;
			mba.setAmount(0, DefaultValues.TILESIZE);
			break;

		case EAST:

			anim = animator.animRIGHT;
			mba.setAmount(DefaultValues.TILESIZE, 0);				
			break;
		
		case SOUTH:

			anim = animator.animDOWN;
			mba.setAmount(0, -DefaultValues.TILESIZE);
			break;
		
		case WEST:

			anim = animator.animLEFT;
			mba.setAmount(-DefaultValues.TILESIZE, 0);
			break;

		case NORTHEAST:
			anim = animator.animNE;
			mba.setAmount(DefaultValues.TILESIZE, DefaultValues.TILESIZE);
			break;
			
		case NORTHWEST:
			anim = animator.animNE;
			mba.setAmount(-DefaultValues.TILESIZE, DefaultValues.TILESIZE);
			break;
			
		case SOUTHEAST:
			anim = animator.animNE;
			mba.setAmount(DefaultValues.TILESIZE, -DefaultValues.TILESIZE);
			break;
			
		case SOUTHWEST:
			anim = animator.animNE;
			mba.setAmount(-DefaultValues.TILESIZE, -DefaultValues.TILESIZE);
			break;
			
		}

		
		
		
		
		
		mba.setDuration(1f);
		this.addAction(mba);

		moveFlag = true;
	
	}
	
	
private void doMovementTurn() {

	switch (getfacing()) {
	case NORTH:
		anim = animator.animStopUP;
		break;
	case EAST:
		anim = animator.animStopRIGHT;
		break;
	case SOUTH:
		anim = animator.animStopDOWN;
		break;
	case WEST:
		anim = animator.animStopLEFT;
		break;
	case NORTHEAST:
		anim = animator.animStopNE;
		break;
	case NORTHWEST:
		anim = animator.animStopNW;
		break;
	case SOUTHEAST:
		anim = animator.animStopSE;
		break;
	case SOUTHWEST:
		anim = animator.animStopSW;
		break;
	}
	MoveByAction mba = new MoveByAction();
	mba.setAmount(0, 0);
	mba.setDuration(1f);
	this.addAction(mba);
	//}
}

	@Override
	public void doMovement(Action action) {
		
		switch(action.gettype())
		{
		case TURN:
			this.rotate(action.getdirection());
			doMovementTurn();
			
			break;
			
		case WALK:
			this.doTilesetWalk();
			doMovementWalk();
			break;
		}
		
		
		//TODO Do render of actor here
		
	}

	
	
	
	


}
