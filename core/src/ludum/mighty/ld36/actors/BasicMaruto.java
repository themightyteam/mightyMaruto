package ludum.mighty.ld36.actors;

import java.util.Vector;

import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by dchaves on 27/08/16.
 */
public class BasicMaruto extends Actor implements BasicActor {
    private String name = DefaultValues.ACTOR_NAME;
    private int life = DefaultValues.ACTOR_LIFE;
    private int punch = DefaultValues.ACTOR_PUNCH_DAMAGE;
    private int speed = DefaultValues.ACTOR_SPEED;
    private boolean visibility = DefaultValues.ACTOR_VISIBILITY;
    private int powerlimit = DefaultValues.ACTOR_MAX_POWERUPS;
    private Vector<Powerup> powerups;
    private DefaultValues.ABSOLUTE_DIRECTIONS facing;
	private int tilePosX;
	private int tilePosY;
	private boolean isPlayable = false; // If this basic Maruto is controlled by
										// the user

    @Override
    public void move(int x, int y) {
        // TODO: Update position
    }

    @Override
    public void shoot(DefaultValues.RELATIVE_DIRECTIONS direction) {

    }

    @Override
    public void pickup(Powerup powerup) {
        if (this.powerups.size() >= this.powerlimit) { // If the actor does not have space for a new powerup
            // TODO: error message (audio)?
        } else { // If the actor has space for a new powerup
            this.powerups.add(powerup);
        }
    }

    @Override
    // Drop powerup by name
    public void drop(String name) {
        for (Powerup pup : this.powerups) {
            if (pup.getname().compareToIgnoreCase(name) == 0) {
                if(pup.candrop()) {
                    this.powerups.remove(pup);
                } else { // Powerup cannot be dropped
                    // TODO: error message (audio)?
                }
                return;
            }
        }
        // Powerup not found in inventory
        // TODO: error message (audio)?
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

    @Override
    public void setname(String name) {
        this.name = name;
    }

    @Override
    public void punch(DefaultValues.RELATIVE_DIRECTIONS direction) {

    }

    @Override
    public String getname() {
        return this.name;
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
    public DefaultValues.ABSOLUTE_DIRECTIONS getfacing() {
        return this.facing;
    }

	@Override
	public boolean isPlayable() {
		return isPlayable;
	}

	@Override
	public void setPlayable(boolean playable) {
		this.isPlayable = playable;
	}

	@Override
	public int getTilePosX() {
		return this.tilePosX;
	}

	@Override
	public void setTilePosX(int newPos) {
		this.tilePosX = newPos;
		this.setX(newPos * DefaultValues.TILESIZE);
	}

	@Override
	public int getTilePosY() {
		return this.tilePosY;
	}

	@Override
	public void setTilePosY(int newPos) {
		this.tilePosY = newPos;
		this.setY(newPos * DefaultValues.TILESIZE);
	}
}
