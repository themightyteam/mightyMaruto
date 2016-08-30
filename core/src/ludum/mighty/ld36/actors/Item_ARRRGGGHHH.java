package ludum.mighty.ld36.actors;

import java.util.ArrayList;
import java.util.Random;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorArrrggghhh;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_ARRRGGGHHH extends Actor_Powerup 
{
	
	private AnimatorArrrggghhh animator;

	
	Random generator = new Random();

	public Item_ARRRGGGHHH(BasicMaruto parent)
	{
		super(parent);

		this.turnsLife = DefaultValues.ARRRGGGHHH_TURNS_LIFE;
		this.life = Integer.MAX_VALUE;
		this.shiftProbability = (float) DefaultValues.ARRRGGGHHH_SHIFT_PROB;
		this.punch = DefaultValues.ARRRGGGHHH_DAMAGE;
		this.speed = DefaultValues.ARRRGGGHHH_SPEED;
		this.name = DefaultValues.POWERUPS.ARRRGGGHHH.toString();
		
		// Put an idle action by default
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add(new Action(DefaultValues.ACTIONS.IDLE));

		this.movementList = actionList;

		this.animator = new AnimatorArrrggghhh("items_spreadsheet.png");
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
		

		// FIXME as the AI is not working put next action here.



	}

	
	
	
	


}
