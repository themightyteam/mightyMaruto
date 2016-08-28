package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_ARRRGGGHHH extends Actor_Powerup 
{
	
	public Item_ARRRGGGHHH()
	{
		this.turnsLife = DefaultValues.ARRRGGGHHH_TURNS_LIFE;
		this.life = Integer.MAX_VALUE;
		this.shiftProbability = (float) DefaultValues.ARRRGGGHHH_SHIFT_PROB;
		this.punch = DefaultValues.ARRRGGGHHH_DAMAGE;
		this.speed = DefaultValues.ARRRGGGHHH_SPEED;
	}

	@Override
	public void doMovement(Action action) {
		
		switch(action.gettype())
		{
		case TURN:
			this.rotate(action.getdirection());
			
			break;
			
		case WALK:
			this.doTilesetWalk();
			
			break;
		}
		
		
		//TODO Do render of actor here
		
	}

	
	
	
	


}
