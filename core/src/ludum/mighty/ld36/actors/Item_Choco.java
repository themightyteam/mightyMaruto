package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_Choco extends Powerup 
{
	
	public Item_Choco()
	{
		this.turnsLife = DefaultValues.CHOCO_TURNS_LIFE;
		this.life = 1;
		this.shiftProbability = (float) DefaultValues.CHOCO_SHIFT_PROB;
		this.punch = DefaultValues.CHOCO_DAMAGE;
		this.speed = DefaultValues.CHOCO_SPEED;
	}

	@Override
	public void doMovement(Action action) {
		

		
		
		//TODO Do render of actor here
		
	}

	
	
	
	


}
