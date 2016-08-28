package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.settings.DefaultValues.ABSOLUTE_DIRECTIONS;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class CommonActor extends Actor implements BasicActor {

	String name;

	boolean visibility = DefaultValues.ACTOR_VISIBILITY;

	DefaultValues.ABSOLUTE_DIRECTIONS facing;
	int tilePosX;
	int tilePosY;

	int nextTilePosX;
	int nexTilePosY;

	boolean isPlayable = false; // If this basic Maruto is controlled by
	// the user

	int life;
	int punch;
	int speed;
	float shiftProbability;
	
	boolean canBeHit;

	ArrayList<Action> movementList;

	//This flag is true if previous movement was finished (true by default)
	boolean moveFlag = false;

	//Respawn
	boolean isrespawnable;
	int turnsToRespawn = DefaultValues.TURNS_TO_RESPAWN;


	public abstract void doMovement(Action action);

	@Override
	public void updateMovementList(ArrayList<Action> movementList) 
	{
		this.movementList = movementList;

	}


	@Override
	public Action getNextAction() {
		// TODO Auto-generated method stub
		//TODO return next action from parser in the case of maruto player

		return null;
	}

	//Getters and setters


	public void doTilesetWalk()
	{
		//TODO: check validity of movements

		//Change the tileset
		if (this.facing == DefaultValues.ABSOLUTE_DIRECTIONS.NORTH)
		{
			this.tilePosY = this.tilePosY + 1;

		}
		else if (this.facing == DefaultValues.ABSOLUTE_DIRECTIONS.WEST)
		{
			this.tilePosX = this.tilePosX - 1;
		}
		else if (this.facing == DefaultValues.ABSOLUTE_DIRECTIONS.EAST)
		{
			this.tilePosX = this.tilePosX + 1;
		}
		else if (this.facing == DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH)
		{
			this.tilePosY = this.tilePosY - 1;
		}
	}


	@Override
	public void rotate(DefaultValues.RELATIVE_ROTATIONS rotation) {
		switch (this.facing) {
		case NORTH:
			switch (rotation) {
			case RIGHT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.EAST;
				return;
			case LEFT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.WEST;
				return;
			}
			break;
		case SOUTH:
			switch (rotation) {
			case RIGHT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.WEST;
				return;
			case LEFT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.EAST;
				return;
			}
			break;
		case EAST:
			switch (rotation) {
			case RIGHT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH;
				return;
			case LEFT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.NORTH;
				return;
			}
			break;
		case WEST:
			switch (rotation) {
			case RIGHT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.NORTH;
				return;
			case LEFT:
				this.facing = DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH;
				return;
			}
			break;
		}
	}



	public int getTilePosX() {
		return tilePosX;
	}
	public void setTilePosX(int tilePosX) {
		this.tilePosX = tilePosX;
		this.setX(this.tilePosX * DefaultValues.TILESIZE);
	}
	public int getTilePosY() {
		return tilePosY;
	}
	public void setTilePosY(int tilePosY) {
		this.tilePosY = tilePosY;
		this.setY(this.tilePosY * DefaultValues.TILESIZE);
	}

	public boolean isMoveFlag() {
		return moveFlag;
	}

	public void setMoveFlag(boolean moveFlag) {
		this.moveFlag = moveFlag;
	}

	@Override
	public void move(int x, int y) {
		// TODO  update position (is this in tiles??)	
	}


	@Override
	public void setname(String name) {
		this.name = name;
	}

	@Override
	public String getname() {
		return name;
	}

	@Override
	public int getlife() {
		return this.life;
	}

	@Override
	public int getspeed() {
		return this.speed;
	}

	@Override
	public int getpunch() {
		return this.punch;
	}

	@Override
	public boolean getvisibility() {
		return this.visibility;
	}

	@Override
	public ABSOLUTE_DIRECTIONS getfacing() {
		return this.facing;
	}

	public boolean isPlayable() {
		return this.isPlayable;
	}


	@Override
	public void setPlayable(boolean playable) {
		this.isPlayable = playable;
	}

	public ArrayList<Action> getMovementList() {
		return movementList;
	}

	public void setMovementList(ArrayList<Action> movementList) {
		this.movementList = movementList;
	}

	@Override
	public int setlife(int life) {
		this.life = life;
		return this.life;
	}

	public float getShiftProbability() {
		return shiftProbability;
	}

	public void setShiftProbability(float shiftProbability) {
		this.shiftProbability = shiftProbability;
	}

	public boolean isCanBeHit() {
		return canBeHit;
	}

	public void setCanBeHit(boolean canBeHit) {
		this.canBeHit = canBeHit;
	}

	public boolean isIsrespawnable() {
		return isrespawnable;
	}

	public void setIsrespawnable(boolean isrespawnable) {
		this.isrespawnable = isrespawnable;
	}

	public int getTurnsToRespawn() {
		return turnsToRespawn;
	}

	public void setTurnsToRespawn(int turnsToRespawn) {
		this.turnsToRespawn = turnsToRespawn;
	}

	
	
}
