package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

public class Item_Punch extends Powerup {

	public Item_Punch() {
		this.turnsLife = DefaultValues.PUNCH_TURNS_LIFE;
		this.life = 1;
		this.shiftProbability = DefaultValues.PUNCH_SHIFT_PROB;
		this.speed = DefaultValues.PUNCH_SPEED; // No speed in punch
		this.punch = DefaultValues.PUNCH_DAMAGE;
	}

	@Override
	public void doMovement(Action action) {
		// Nothing inside (there is no need to complete this)
	}

}
