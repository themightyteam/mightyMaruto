package ludum.mighty.ld36.actors;

import java.util.Vector;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.animations.AnimatorMaruto;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by dchaves on 27/08/16.
 */
public class BasicMaruto extends CommonActor implements BasicActor {



	private int powerlimit = DefaultValues.ACTOR_MAX_POWERUPS;
	private Vector<Item_Powerup> powerups;

    private AnimatorMaruto animator;
	private Animation anim;
	
	public int KID_WIDTH = 32;
	public int KID_HEIGHT = 32;

	private float elapsedTime = 0;

	private MoveByAction mba;

	// private boolean stopFlag;

	Action nextAction;

	public void setNextAction(Action nextAction) {
		this.nextAction = nextAction;
	}

	public BasicMaruto(String textureSheet) {

		this.name = DefaultValues.ACTOR_NAME;



		this.life = DefaultValues.ACTOR_LIFE;
		this.punch = DefaultValues.ACTOR_PUNCH_DAMAGE;
		this.speed = DefaultValues.ACTOR_SPEED;
		this.isrespawnable = true;
		this.canBeHit = true;
		// stopFlag = false;

		facing = DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH;

		animator = new AnimatorMaruto(textureSheet);

		setBounds(getX(), getY(), DefaultValues.TILESIZE, DefaultValues.TILESIZE);

		this.powerups = new Vector<Item_Powerup>();

	}


	@Override
	public void act(float delta) {
		super.act(delta);
		if (this.getActions().size == 0) {
			moveFlag = false;

			// Animacion de parado
			switch (getfacing()) {
			case NORTH:
				anim = animator.animStopUP;
				break;
			case EAST:
				anim = animator.animStopRIGHT;
				break;
			case SOUTH:
				anim = animator.animStopDOWN;
				break;
			case WEST:
				anim = animator.animStopLEFT;
				break;
			}
			mba = new MoveByAction();
			mba.setAmount(0, 0);
			mba.setDuration(5f);
			this.addAction(mba);
			// moveFlag = true;
			// stopFlag = true;
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
	public void pickup(Item_Powerup powerup) {
		if (this.powerups.size() >= this.powerlimit) { // If the actor does not have space for a new powerup
			// TODO: error message (audio)?
		} else { // If the actor has space for a new powerup

			// Check of a powerup with this name already exists
			for (Item_Powerup item : this.powerups) {
				if (item.getName() == powerup.getName()) {
					// an item with the same name already exists
					return;
				}
			}

			if (powerup.getType() == DefaultValues.POWERUPS.RING) {
				this.punch += powerup.getStrengthPowerup();
			}
			else if (powerup.getType() == DefaultValues.POWERUPS.SNEAKERS)
			{
				this.speed += powerup.getSpeedPowerup();
			}
			else if (powerup.getType() == DefaultValues.POWERUPS.INVISIBILITY)
			{
				this.visibility = false;
			}
			else if (powerup.getType() == DefaultValues.POWERUPS.DIZZY)
			{
				this.dizzyness = true;
			} else if (powerup.getType() == DefaultValues.POWERUPS.SHIELD) {
				this.shielded = true;
			}

			this.powerups.add(powerup);
		}
	}

	@Override
	// Drop powerup by name
	public void drop(String name) {
		for (Item_Powerup pup : this.powerups) {
			if (pup.getName().compareToIgnoreCase(name) == 0) {
				if(pup.candrop()) {

					if (pup.getType() == DefaultValues.POWERUPS.INVISIBILITY)
					{
						this.visibility = true;
					}
 else if (pup.getType() == DefaultValues.POWERUPS.SHIELD) {
						this.shielded = false;
					} else if (pup.getType() == DefaultValues.POWERUPS.RING) {
						this.punch -= pup.getStrengthPowerup();
					} else if (pup.getType() == DefaultValues.POWERUPS.SNEAKERS) {
						this.speed -= pup.getSpeedPowerup();
					}

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



	/*
	 * public void checkAction() { if (this.getActions().size > 1) return; if
	 * ((this.getActions().size == 1)&&(stopFlag == true)) {
	 * 
	 * nextAction = commandProcessor.getNextAction(); if (nextAction == null)
	 * return; switch (nextAction.gettype()) {
	 * 
	 * case WALK: //if (moveFlag == false) { mba = new MoveByAction(); switch
	 * (getfacing()) { case NORTH: anim = animUP; mba.setAmount(0,
	 * DefaultValues.TILESIZE); break; case EAST: anim = animRIGHT;
	 * mba.setAmount(DefaultValues.TILESIZE, 0); break; case SOUTH: anim =
	 * animDOWN; mba.setAmount(0, -DefaultValues.TILESIZE); break; case WEST:
	 * anim = animLEFT; mba.setAmount(-DefaultValues.TILESIZE, 0); break; }
	 * mba.setDuration(1f); this.addAction(mba);
	 * 
	 * moveFlag = true;
	 * 
	 * //} break; case MOONWALK: //if (moveFlag == false) { mba = new
	 * MoveByAction(); switch (getfacing()) { case NORTH: anim = animUP;
	 * mba.setAmount(0, -DefaultValues.TILESIZE); break; case EAST: anim =
	 * animRIGHT; mba.setAmount(-DefaultValues.TILESIZE, 0); break; case SOUTH:
	 * anim = animDOWN; mba.setAmount(0, DefaultValues.TILESIZE); break; case
	 * WEST: anim = animLEFT; mba.setAmount(DefaultValues.TILESIZE, 0); break; }
	 * 
	 * mba.setDuration(1f); this.addAction(mba);
	 * 
	 * 
	 * //} break; case SHOOT:
	 * 
	 * // The shoot animation is independent of the the item type
	 * switch(nextAction.getpowerup()) { case PUNCH: //if (moveFlag == false) {
	 * switch (getfacing()) { case NORTH: anim = animPunchUP; break; case EAST:
	 * anim = animPunchRIGHT; break; case SOUTH: anim = animPunchDOWN; break;
	 * case WEST: anim = animPunchLEFT; break; } mba = new MoveByAction();
	 * mba.setAmount(0, 0); mba.setDuration(1f); this.addAction(mba);
	 * 
	 * moveFlag = true; //} break; }
	 * 
	 * break;
	 * 
	 * case TURN: //if (moveFlag == false) {
	 * this.rotate(nextAction.getdirection()); switch (getfacing()) { case
	 * NORTH: anim = animStopUP; break; case EAST: anim = animStopRIGHT; break;
	 * case SOUTH: anim = animStopDOWN; break; case WEST: anim = animStopLEFT;
	 * break; } mba = new MoveByAction(); mba.setAmount(0, 0);
	 * mba.setDuration(1f); this.addAction(mba); //} break; }
	 * 
	 * }
	 * 
	 * }
	 */

	
	
	private void doMovementWalk() {
		
		mba = new MoveByAction();
		
		switch (facing) {

		case NORTH:

			// Logic here (moving tiles)
			this.tilePosY = this.tilePosY + 1;

			anim = animator.animUP;
			mba.setAmount(0, DefaultValues.TILESIZE);
			break;

		case EAST:

			// Logic here (moving tiles)
			this.tilePosX = this.tilePosX + 1;

			anim = animator.animRIGHT;
			mba.setAmount(DefaultValues.TILESIZE, 0);				
			break;
		
		case SOUTH:
			// Logic here
			this.tilePosY = this.tilePosY - 1;

			anim = animator.animDOWN;
			mba.setAmount(0, -DefaultValues.TILESIZE);
			break;
		
		case WEST:

			// Logic here
			this.tilePosX = this.tilePosX - 1;

			anim = animator.animLEFT;
			mba.setAmount(-DefaultValues.TILESIZE, 0);
			break;
		}
		
		mba.setDuration(1f);
		this.addAction(mba);

		moveFlag = true;
	
	}
	
	
	
	private void doMovementMoonWalk() {
		//if (moveFlag == false) {
		mba = new MoveByAction();
		switch (getfacing()) {
		case NORTH:

			// Logic here (moving tiles)
			this.tilePosY = this.tilePosY - 1;

			anim = animator.animUP;
			mba.setAmount(0, -DefaultValues.TILESIZE);
			break;
		case EAST:

			// Logic here (moving tiles)
			this.tilePosX = this.tilePosX - 1;

			anim = animator.animRIGHT;
			mba.setAmount(-DefaultValues.TILESIZE, 0);				
			break;
		case SOUTH:

			// Logic here (moving tiles)
			this.tilePosY = this.tilePosY + 1;

			anim = animator.animDOWN;
			mba.setAmount(0, DefaultValues.TILESIZE);
			break;
		case WEST:

			// Logic here (moving tiles)
			this.tilePosX = this.tilePosX + 1;

			anim = animator.animLEFT;
			mba.setAmount(DefaultValues.TILESIZE, 0);
			break;
		}

		mba.setDuration(1f);
		this.addAction(mba);

	}
	
	private void doMovementShoot() {
		switch (facing) {
		case NORTH:
			anim = animator.animPunchUP;
			break;
		case EAST:
			anim = animator.animPunchRIGHT;
			break;
		case SOUTH:
			anim = animator.animPunchDOWN;
			break;
		case WEST:
			anim = animator.animPunchLEFT;
			break;
		}
		mba = new MoveByAction();
		mba.setAmount(0, 0);
		mba.setDuration(1f);
		this.addAction(mba);

		moveFlag = true;
	}
	
	
	private void doMovementTurn() {
		this.rotate(nextAction.getdirection());
		switch (getfacing()) {
		case NORTH:
			anim = animator.animStopUP;
			break;
		case EAST:
			anim = animator.animStopRIGHT;
			break;
		case SOUTH:
			anim = animator.animStopDOWN;
			break;
		case WEST:
			anim = animator.animStopLEFT;
			break;
		}
		mba = new MoveByAction();
		mba.setAmount(0, 0);
		mba.setDuration(1f);
		this.addAction(mba);
		//}
	}

	
	
	private void doMovementBlock() {
		switch (getfacing()) {
		case NORTH:
			anim = animator.animBlockUP;
			break;
		case EAST:
			anim = animator.animBlockRIGHT;
			break;
		case SOUTH:
			anim = animator.animBlockDOWN;
			break;
		case WEST:
			anim = animator.animBlockLEFT;
			break;
		}
		mba = new MoveByAction();
		mba.setAmount(0, 0);
		mba.setDuration(1f);
		this.addAction(mba);

		moveFlag = true;
	}
	
	
	
	public void doMovementConfusion() {
		anim = animator.animBirds;
		mba = new MoveByAction();
		mba.setAmount(0, 0);
		mba.setDuration(5f);
	}
	
	
	
	public void doMovementHit() {
		switch (getfacing()) {
		case NORTH:
			anim = animator.animHitUP;
			break;
		case EAST:
			anim = animator.animHitRIGHT;
			break;
		case SOUTH:
			anim = animator.animHitDOWN;
			break;
		case WEST:
			anim = animator.animHitLEFT;
			break;
		}
		mba = new MoveByAction();
		mba.setAmount(0, 0);
		mba.setDuration(1f);
		this.addAction(mba);

		moveFlag = true;		
	}
	
	
	
	public void doMovementShiftHit() {
		mba = new MoveByAction();
		switch (getfacing()) {
		case NORTH:

			// Logic here (moving tiles)
			this.tilePosY = this.tilePosY - 1;

			anim = animator.animHitUP;
			mba.setAmount(0, -DefaultValues.TILESIZE);
			break;
		case EAST:

			// Logic here (moving tiles)
			this.tilePosX = this.tilePosX - 1;

			anim = animator.animHitRIGHT;
			mba.setAmount(-DefaultValues.TILESIZE, 0);				
			break;
		case SOUTH:

			// Logic here (moving tiles)
			this.tilePosY = this.tilePosY + 1;

			anim = animator.animHitDOWN;
			mba.setAmount(0, DefaultValues.TILESIZE);
			break;
		case WEST:

			// Logic here (moving tiles)
			this.tilePosX = this.tilePosX + 1;

			anim = animator.animHitLEFT;
			mba.setAmount(DefaultValues.TILESIZE, 0);
			break;
		}
		mba.setDuration(1f);
		this.addAction(mba);

		moveFlag = true;		
	}	
	
	
	private void doMovementDeath() {
		anim = animator.animDeath;
		moveFlag = true;
	}
	
	
	@Override
	public void doMovement(Action action) {
		// TODO Auto-generated method stub


		switch (action.gettype()) {

		case WALK:
			doMovementWalk();	
			break;
		case MOONWALK:
			doMovementMoonWalk();
			break;
		case SHOOT:
			doMovementShoot();
			break;
		case TURN:
			doMovementTurn();
			break;
		case BLOCK:
			doMovementBlock();
			break;
		case CONFUSION:
			doMovementConfusion();
			break;	
		case DEATH:
			doMovementDeath();
			break;
		case DROP:
			break;
		case HELP:
			break;
		case HIT:
			doMovementHit();
			break;
		case IDLE:
			break;
		case PICK:
			break;
		case RUN:
			break;
		case SHIFT_HIT:
			doMovementShiftHit();
			break;
		case STOP:
			break;
		
		default:
			System.out.println("Action Type Unknown: " + action.gettype());
			break;
		}




	}


	public Vector<Item_Powerup> getPowerups() {
		return powerups;
	}

	public void setPowerups(Vector<Item_Powerup> powerups) {
		this.powerups = powerups;
	}

}











