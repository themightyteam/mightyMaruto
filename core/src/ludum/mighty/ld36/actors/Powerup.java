package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.settings.DefaultValues.RELATIVE_DIRECTIONS;
import ludum.mighty.ld36.settings.DefaultValues.RELATIVE_ROTATIONS;
import ludum.mighty.ld36.settings.DefaultValues.STATE_MOVEMENTS;

/**
 * Created by dchaves on 27/08/16.
 */
public abstract class Powerup extends CommonActor {
    private boolean canbedropped = DefaultValues.POWERUP_CAN_BE_DROPPED;
    
    boolean isLifeLimitedByTime;
    int turnsLife;
    
    public Powerup()
    {
    	this.isrespawnable = false;
    	this.isLifeLimitedByTime = true;
    	this.turnsLife = DefaultValues.POWERUP_TURNS_LIFE;
    	this.canBeHit = false;
    }
    
    
    public boolean candrop() {
        return this.canbedropped;
    }


	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot(RELATIVE_DIRECTIONS direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickup(Powerup powerup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(String powerup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(RELATIVE_ROTATIONS rotation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void punch(RELATIVE_DIRECTIONS direction) {
		// TODO Auto-generated method stub
		
	}


	public boolean isLifeLimitedByTime() {
		return isLifeLimitedByTime;
	}


	public void setLifeLimitedByTime(boolean isLifeLimitedByTime) {
		this.isLifeLimitedByTime = isLifeLimitedByTime;
	}


	public int getTurnsLife() {
		return turnsLife;
	}


	public void setTurnsLife(int turnsLife) {
		this.turnsLife = turnsLife;
	}










}
