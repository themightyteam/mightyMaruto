package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.settings.DefaultValues.RELATIVE_DIRECTIONS;
import ludum.mighty.ld36.settings.DefaultValues.RELATIVE_ROTATIONS;

/**
 * Created by dchaves on 27/08/16.
 */
public abstract class Actor_Powerup extends CommonActor {

    
    boolean isLifeLimitedByTime;
    int turnsLife;
    
	boolean isHarmfull = true; // if makes damage on touch

	public boolean isHarmfull() {
		return isHarmfull;
	}

	public void setHarmfull(boolean isHarmfull) {
		this.isHarmfull = isHarmfull;
	}

	public Actor_Powerup()
    {
    	this.isrespawnable = false;
    	this.isLifeLimitedByTime = true;
    	this.turnsLife = DefaultValues.POWERUP_TURNS_LIFE;
    	this.canBeHit = false;
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


	@Override
	public void pickup(Item_Powerup powerup) {
		// TODO Auto-generated method stub

	}







}
