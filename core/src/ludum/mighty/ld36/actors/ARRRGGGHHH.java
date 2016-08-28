package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.settings.DefaultValues.STATE_MOVEMENTS;

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
	public void doMovement(STATE_MOVEMENTS move) {
		
		switch(move)
		{
		case ROTATE_LEFT:
			this.rotate(DefaultValues.RELATIVE_ROTATIONS.LEFT);
			
			break;
			
		case ROTATE_RIGHT:
			this.rotate(DefaultValues.RELATIVE_ROTATIONS.RIGHT);
			
			break;
			
		case WALK:
			this.doTilesetWalk();
			
			break;
		}
		
		
		//TODO Do render of actor here
		
	}

	
	
	
	


}
