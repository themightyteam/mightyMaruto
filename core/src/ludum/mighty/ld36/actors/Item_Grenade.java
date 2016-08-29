package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorGrenade;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Item_Grenade extends Actor_Powerup {

	private AnimatorGrenade animator;

	public Item_Grenade(BasicMaruto parent) {

		super(parent);

		this.turnsLife = DefaultValues.GRENADE_TURNS_LIFE;
		this.life = DefaultValues.GRENADE_LIFE;
		this.shiftProbability = DefaultValues.GRENADE_SHIFT_PROB;
		this.speed = DefaultValues.GRENADE_SPEED; // No speed in punch
		this.punch = DefaultValues.GRENADE_DAMAGE;
		this.name = DefaultValues.POWERUPS.GRENADE.toString();
		// Put an idle action by default
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add(new Action(DefaultValues.ACTIONS.IDLE));


		/**
		 * // Put an idle action by default ArrayList<Action> actionList = new
		 * ArrayList<Action>(); actionList.add(new
		 * Action(DefaultValues.ACTIONS.IDLE));
		 **/

		this.movementList = actionList;

		this.animator = new AnimatorGrenade("items_spreadsheet.png");
		anim = animator.anim;

	}

	public void doMovementIdle() {
		anim = animator.animStopGrenade;
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0, 0);
		mba.setDuration(1f);
		this.addAction(mba);
	}

	@Override
	public void doMovement(Action action) {
		// TODO Auto-generated method stub

		doMovementIdle();
	}

}
