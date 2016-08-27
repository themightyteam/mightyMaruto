package ludum.mighty.ld36.actors;

import java.util.Vector;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.textTerminal.CommandProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

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

	private Texture kidTexture;
	private TextureRegion[][] kidTR;
	private TextureRegion[][] kidTRflip;
	public Animation animUP, animDOWN, animLEFT, animRIGHT,
	                 animPunchUP, animPunchDOWN, animPunchLEFT, animPunchRIGHT,
	                 animBlockUP, animBlockDOWN, animBlockLEFT, animBlockRIGHT,
	                 animBirds, anim;
	public int KID_WIDTH = 32;
	public int KID_HEIGHT = 32;

	private float elapsedTime = 0;

	private MoveByAction mba;
	private boolean moveFlag = false;
	
	private CommandProcessor commandProcessor;
	
	public BasicMaruto(CommandProcessor cm) {
		
		commandProcessor = cm;
		
		kidTexture = new Texture("maruto_spreadsheet.png");
		kidTR = TextureRegion.split(kidTexture, KID_WIDTH, KID_HEIGHT);
		kidTRflip = TextureRegion.split(kidTexture, KID_WIDTH, KID_HEIGHT);
        kidTRflip[0][3].flip(true, false);
        kidTRflip[0][4].flip(true, false);
        kidTRflip[0][5].flip(true, false);

        kidTRflip[2][3].flip(true, false);
        kidTRflip[2][4].flip(true, false);
        kidTRflip[2][5].flip(true, false);
        
        kidTRflip[1][2].flip(true, false);
        
		// Create animations for movement 
		animDOWN  = new Animation(0.25f, kidTR[0][0], kidTR[0][1], kidTR[0][2], kidTR[0][1]);
		animRIGHT  = new Animation(0.25f, kidTR[0][3], kidTR[0][4], kidTR[0][5], kidTR[0][4]);
		animUP    = new Animation(0.25f, kidTR[0][6], kidTR[0][7], kidTR[0][8], kidTR[0][7]);
		animLEFT = new Animation(0.25f, kidTRflip[0][3], kidTRflip[0][4], kidTRflip[0][5], kidTRflip[0][4]);

		animPunchDOWN = new Animation(0.25f, kidTR[0][1], kidTR[2][0], kidTR[2][1], kidTR[2][2]);
		animPunchRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[2][3], kidTR[2][4], kidTR[2][5]);
		animPunchUP = new Animation(0.25f, kidTR[0][7], kidTR[2][6], kidTR[2][7], kidTR[2][8]);
		animPunchLEFT = new Animation(0.25f, kidTRflip[0][5], kidTRflip[2][3], kidTRflip[2][4], kidTRflip[2][5]);		
		
		animBlockDOWN = new Animation(0.25f, kidTR[0][1], kidTR[1][1]);
		animBlockRIGHT = new Animation(0.25f, kidTR[0][4], kidTR[1][2]);
		animBlockUP = new Animation(0.25f, kidTR[0][7], kidTR[1][0]);
		animBlockLEFT = new Animation(0.25f, kidTRflip[0][4], kidTRflip[1][2]);
		
		
		animBirds = new Animation(0.25f, kidTRflip[3][0], kidTRflip[3][1], kidTRflip[3][2], kidTRflip[3][3]);
		// Set initial position of the kid
		anim = animDOWN;
		//setPosition(0, 0);
		//anim.getKeyFrame(0, movement);

		setBounds(getX(), getY(), KID_WIDTH, KID_HEIGHT);


		
		
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (this.getActions().size == 0) {
			moveFlag = false;
		}
		System.out.println("BasicMaruto: " + this.getX() + " " + this.getY());
		
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(anim.getKeyFrame(elapsedTime, moveFlag), getX(), getY());
//		batch.draw(anim.getKeyFrame(elapsedTime, true), getX(), getY());

	}
	
	
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
		
	
	public void checkAction() {
		Action ac = commandProcessor.getNextAction();
		if (ac.gettype() == DefaultValues.ACTIONS.WALK){
			if (moveFlag == false) {
				anim = animDOWN;
				//if (Math.round(getY()) > 0) {
					mba = new MoveByAction();
					mba.setAmount(0,-32);
					mba.setDuration(1f);
					this.addAction(mba);
				//}

				moveFlag = true;

			}
		}
		
		
		
	}
}
