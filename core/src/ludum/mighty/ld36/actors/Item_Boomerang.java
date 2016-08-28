package ludum.mighty.ld36.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorArrrggghhh;
import ludum.mighty.ld36.animations.AnimatorBoomerang;
import ludum.mighty.ld36.animations.AnimatorSonicBoom;
import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_Boomerang extends Actor_Powerup 
{
	
	private AnimatorBoomerang animator;
	private Animation anim;
	
	public Item_Boomerang()
	{
		this.turnsLife = DefaultValues.ARRRGGGHHH_TURNS_LIFE;
		this.life = Integer.MAX_VALUE;
		this.shiftProbability = (float) DefaultValues.ARRRGGGHHH_SHIFT_PROB;
		this.punch = DefaultValues.ARRRGGGHHH_DAMAGE;
		this.speed = DefaultValues.ARRRGGGHHH_SPEED;
		
		this.animator = new AnimatorBoomerang("items_spreadsheet.png");
		anim = animator.anim;
	}

	
private void doMovementWalk() {
		
		MoveByAction mba = new MoveByAction();
		
		anim = animator.animBoomerang;
		switch (facing) {

		case NORTH:
			mba.setAmount(0, DefaultValues.TILESIZE);
			break;

		case EAST:
			mba.setAmount(DefaultValues.TILESIZE, 0);				
			break;
		
		case SOUTH:
			mba.setAmount(0, -DefaultValues.TILESIZE);
			break;
		
		case WEST:
			mba.setAmount(-DefaultValues.TILESIZE, 0);
			break;

		case NORTHEAST:
			mba.setAmount(DefaultValues.TILESIZE, DefaultValues.TILESIZE);
			break;
			
		case NORTHWEST:
			mba.setAmount(-DefaultValues.TILESIZE, DefaultValues.TILESIZE);
			break;
			
		case SOUTHEAST:
			mba.setAmount(DefaultValues.TILESIZE, -DefaultValues.TILESIZE);
			break;
			
		case SOUTHWEST:
			mba.setAmount(-DefaultValues.TILESIZE, -DefaultValues.TILESIZE);
			break;
			
		}

		
		
		
		
		
		mba.setDuration(1f);
		this.addAction(mba);

		moveFlag = true;
	
	}
	
	
private void doMovementTurn() {

	anim = animator.animStopBoomerang;
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
