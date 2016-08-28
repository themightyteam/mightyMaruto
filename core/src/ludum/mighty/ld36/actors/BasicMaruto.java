package ludum.mighty.ld36.actors;

import java.util.Vector;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.settings.DefaultValues.STATE_MOVEMENTS;
import ludum.mighty.ld36.textTerminal.CommandProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by dchaves on 27/08/16.
 */
public class BasicMaruto extends CommonActor implements BasicActor {
    
  
   
    private int powerlimit = DefaultValues.ACTOR_MAX_POWERUPS;
    private Vector<Powerup> powerups;

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
	
	public BasicMaruto(CommandProcessor cm, String textureSheet) {
		
		this.name = DefaultValues.ACTOR_NAME;
		
		this.life = DefaultValues.ACTOR_LIFE;
		this.punch = DefaultValues.ACTOR_PUNCH_DAMAGE;
		this.speed = DefaultValues.ACTOR_SPEED;
		this.isrespawnable = true;
		this.canBeHit = true;
		
		facing = DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH;
		commandProcessor = cm;
		
		//FIXME pass this string as a parameter, so you can use this class for maruto and evil maruto
		kidTexture = new Texture(textureSheet);
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
		//System.out.println("BasicMaruto: " + this.getX() + " " + this.getY());
		
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
    public void punch(DefaultValues.RELATIVE_DIRECTIONS direction) {

    }
    
   
 
	
	public void checkAction() {
		if (this.getActions().size > 0) return;
		Action ac = commandProcessor.getNextAction();
		if (ac == null) return;
		switch (ac.gettype()) {
		case WALK:
			if (moveFlag == false) {
				mba = new MoveByAction();
				if (anim == animDOWN) {
					mba.setAmount(0, -DefaultValues.TILESIZE);
				} else if (anim == animUP) {
					mba.setAmount(0, DefaultValues.TILESIZE);
				} else if (anim == animRIGHT) {
					mba.setAmount(DefaultValues.TILESIZE, 0);
				} else if (anim == animLEFT) {
					mba.setAmount(-DefaultValues.TILESIZE, 0);
				}
					mba.setDuration(1f);
					this.addAction(mba);

				moveFlag = true;

			}			
			break;
		case MOONWALK:
			if (moveFlag == false) {
				mba = new MoveByAction();
				if (anim == animDOWN) {
					mba.setAmount(0, DefaultValues.TILESIZE);
				} else if (anim == animUP) {
					mba.setAmount(0, -DefaultValues.TILESIZE);
				} else if (anim == animRIGHT) {
					mba.setAmount(-DefaultValues.TILESIZE, 0);
				} else if (anim == animLEFT) {
					mba.setAmount(DefaultValues.TILESIZE, 0);
				}
					mba.setDuration(1f);
					this.addAction(mba);

				moveFlag = true;

			}			
			break;
		case PUNCH:

				if (moveFlag == false) {
				if (anim == animDOWN) {
					anim = animPunchDOWN;
				} else if (anim == animUP) {
					anim = animPunchUP;
				} else if (anim == animRIGHT) {
					anim = animPunchRIGHT;
				} else if (anim == animLEFT) {
					anim = animPunchLEFT;
				}
				mba = new MoveByAction();
				mba.setAmount(0, 0);
				mba.setDuration(1f);
				this.addAction(mba);
				
				moveFlag = true;
				}
				break;
		case TURN:
				if (moveFlag == false) {
					this.rotate(ac.getdirection());
					switch (getfacing()) {
					case NORTH:
						anim = animUP;
						break;
					case EAST:
						anim = animRIGHT;
						break;
					case SOUTH:
						anim = animDOWN;
						break;
					case WEST:
						anim = animLEFT;
						break;
					}
					mba = new MoveByAction();
					mba.setAmount(0, 0);
					mba.setDuration(1f);
					this.addAction(mba);
				}
		}
		


			
		
		
	}


	@Override
	public void doMovement(STATE_MOVEMENTS move) {
		// TODO Auto-generated method stub
		
	}








}