package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorChoco;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_Choco extends Actor_Powerup 
{
	
	AnimatorChoco animator;

	public Item_Choco(BasicMaruto parent)
	{
		super(parent);

		this.turnsLife = DefaultValues.CHOCO_TURNS_LIFE;
		this.life = DefaultValues.CHOCO_LIFE;
		this.shiftProbability = (float) DefaultValues.CHOCO_SHIFT_PROB;
		this.punch = DefaultValues.CHOCO_DAMAGE;
		this.speed = DefaultValues.CHOCO_SPEED;
		this.name = DefaultValues.POWERUPS.CHOCO.toString();

		// Put an idle action by default
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add(new Action(DefaultValues.ACTIONS.IDLE));

		this.movementList = actionList;

		this.animator = new AnimatorChoco("items_spreadsheet.png");
		anim = animator.anim;

	}

	public void doMovementIdle() {
		anim = animator.animStopChoco;
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0, 0);
		mba.setDuration(1f);
		this.addAction(mba);
	}

	@Override
	public void doMovement(Action action) {
		doMovementIdle();
	}
	
	
	
	


}
