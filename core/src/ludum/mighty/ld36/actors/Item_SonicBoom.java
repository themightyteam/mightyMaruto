package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorSonicBoom;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_SonicBoom extends Actor_Powerup 
{

	private AnimatorSonicBoom animator;

	public Item_SonicBoom(BasicMaruto parent)
	{
		super(parent);

		this.turnsLife = DefaultValues.SONICBOMB_TURNS_LIFE;
		this.life = DefaultValues.SONICBOMB_LIFE;
		this.shiftProbability = (float) DefaultValues.SONICBOMB_SHIFT_PROB;
		this.punch = DefaultValues.SONICBOMB_DAMAGE;
		this.speed = DefaultValues.SONICBOMB_SPEED;
		this.name = DefaultValues.POWERUPS.SONICBOMB.toString();

		// Put an idle action by default
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add(new Action(DefaultValues.ACTIONS.WALK));

		this.movementList = actionList;

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
		// }
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
