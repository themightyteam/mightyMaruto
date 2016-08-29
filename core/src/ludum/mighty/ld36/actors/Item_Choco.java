package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Item_Choco extends Actor_Powerup 
{
	
	public Item_Choco(BasicMaruto parent)
	{
		super(parent);

		this.turnsLife = DefaultValues.CHOCO_TURNS_LIFE;
		this.life = 1;
		this.shiftProbability = (float) DefaultValues.CHOCO_SHIFT_PROB;
		this.punch = DefaultValues.CHOCO_DAMAGE;
		this.speed = DefaultValues.CHOCO_SPEED;
		this.name = DefaultValues.POWERUPS.CHOCO.toString();

		// Put an idle action by default
		ArrayList<Action> actionList = new ArrayList<Action>();
		actionList.add(new Action(DefaultValues.ACTIONS.IDLE));

		this.movementList = actionList;
	}

	@Override
	public void doMovement(Action action) {
		

		
		
		//TODO Do render of actor here
		
	}

	
	
	
	


}
