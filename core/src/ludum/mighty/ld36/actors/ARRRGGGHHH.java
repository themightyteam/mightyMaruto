package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class ARRRGGGHHH extends Powerup 
{
	
	public ARRRGGGHHH()
	{
		this.turnsLife = 5;
		this.life = Integer.MAX_VALUE;
		this.shiftProbability = (float) 0.9;
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
