package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorBoomerang;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_Boomerang extends Actor_Powerup 
{
	
	private AnimatorBoomerang animator;
	private Animation anim;
	
	public Item_Boomerang(BasicMaruto parent)
	{
		super(parent);

		this.turnsLife = DefaultValues.RANDOM_TURNS_LIFE;
		this.life = DefaultValues.RANDOM_LIFE;
		this.shiftProbability = (float) DefaultValues.RANDOM_SHIFT_PROB;
		this.punch = DefaultValues.RANDOM_DAMAGE;
		this.speed = DefaultValues.RANDOM_SPEED;
		this.name = DefaultValues.POWERUPS.RANDOM.toString();
		
		// Put an idle action by default
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add(new Action(DefaultValues.ACTIONS.IDLE));

		this.movementList = actionList;

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
