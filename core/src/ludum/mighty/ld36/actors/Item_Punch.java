package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

public class Item_Punch extends Actor_Powerup {

	public Item_Punch(BasicMaruto parent) {
		super(parent);

		this.turnsLife = DefaultValues.PUNCH_TURNS_LIFE;
		this.life = DefaultValues.PUNCH_LIFE;
		this.shiftProbability = DefaultValues.PUNCH_SHIFT_PROB;
		this.speed = DefaultValues.PUNCH_SPEED; // No speed in punch
		this.punch = DefaultValues.PUNCH_DAMAGE;
		this.name = DefaultValues.POWERUPS.PUNCH.toString();
		// Put an idle action by default
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add(new Action(DefaultValues.ACTIONS.IDLE));

		this.movementList = actionList;


	}

	@Override
	public void doMovement(Action action) {
		// Nothing inside (there is no need to complete this)
	}




}
