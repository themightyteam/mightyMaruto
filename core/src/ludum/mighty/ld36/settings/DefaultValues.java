package ludum.mighty.ld36.settings;


/**
 * Created by dchaves on 27/08/16.
 */
public class DefaultValues {
    // Default values for Actors
    public static final String ACTOR_NAME = "Maruto";
	public static final int ACTOR_LIFE = 3;
    public static final int ACTOR_SPEED = 1;
    public static final int ACTOR_PUNCH_DAMAGE = 1;
    public static final boolean ACTOR_VISIBILITY = true;
	public static final int ACTOR_MAX_POWERUPS = 4;
	public static final int TURNS_TO_RESPAWN = 5;

	public static final int ITEM_SNEAKER_SPEED_POWERUP = 2;
	public static final int ITEM_RING_STRENGTH_POWERUP = 3;
	public static final int ITEM_SNEAKER_TURNS_DURATION = 4;
	public static final int ITEM_RING_TURNS_DURATION = 3;
	public static final int ITEM_SHIELD_TURNS_DURATION = 4;
	public static final int ITEM_INVISIBILITY_TURNS_DURATION = 4;
	public static final int ITEM_DIZZY_TURNS_DURATION = 5;
	public static final int ITEM_ARRRGGGHHH_TURNS_DURATION = 3;
	public static final int ITEM_PROYECTIL_TURNS_DURATION = 5;
	public static final int ITEM_YENDOR_TURNS_DURATION = Integer.MAX_VALUE;

    public static final ABSOLUTE_DIRECTIONS ACTOR_DEFAULT_FACING = ABSOLUTE_DIRECTIONS.SOUTH;
    
    public static final int MARUTO_HEADBUMP_DAMAGE = 1;

    // Default values for Powerups
    public static final boolean POWERUP_CAN_BE_DROPPED = true;
    public static final int POWERUP_TURNS_LIFE = 3;
    public static final int ARRRGGGHHH_TURNS_LIFE = 5;
    public static final float ARRRGGGHHH_SHIFT_PROB = (float) 0.9;
	public static final int ARRRGGGHHH_DAMAGE = 10;
	public static final int ARRRGGGHHH_SPEED = 1;
	public static final int PUNCH_TURNS_LIFE = 0;
	public static final float PUNCH_SHIFT_PROB = (float) 0.1;
	public static final int PUNCH_DAMAGE = 2;
	public static final int PUNCH_SPEED = 0;
	public static final int CHOCO_TURNS_LIFE = 3;
	public static final float CHOCO_SHIFT_PROB = (float) 0.7;
	public static final int CHOCO_DAMAGE = 1;
	public static final int CHOCO_SPEED = 3;
	public static final int RANDOM_TURNS_LIFE = 4;
	public static final float RANDOM_SHIFT_PROB = (float) 0.3;
	public static final int RANDOM_DAMAGE = 3;
	public static final int RANDOM_SPEED = 2;
	public static final int GRENADE_DAMAGE = 0;
	public static final int GRENADE_SPEED = 0;
	public static final int GRENADE_TURNS_LIFE = 5;
	public static final float GRENADE_SHIFT_PROB = (float) 0.0;

    // Enumerated values
    public enum ABSOLUTE_DIRECTIONS {NORTH, SOUTH, EAST, WEST}
    public enum RELATIVE_ROTATIONS {RIGHT, LEFT}

    public enum RELATIVE_DIRECTIONS {FORWARD, BACKWADS, RIGHT, LEFT}
   // public enum ACTIONS {WALK, MOONWALK, RUN, PUNCH, STOP, HELP, TURN, DROP, SHOOT}
	public enum POWERUPS {
		ARRRGGGHHH, YENDOR, CHOCO, GRENADE, RANDOM, SHIELD, INVISIBILITY,
        RING, SONICBOMB, DIAG_SONICBOMB, SNEAKERS, DIZZY, PUNCH, BLACKBOX, EXPLOSION
	}


	public enum ACTIONS {
		RUN, WALK, TURN, MOONWALK, SHOOT, PICK, HIT, SHIFT_HIT, DEATH, DROP, IDLE, STOP, HELP, CONFUSION, BLOCK
	}
    	    
   
    // World constants
    public static final int TILESIZE = 32;

	public static final int WORLD_SECONDS_FOR_COMMAND_INPUT = 5;

	// World state
	/*
	 * The user enter commands and a timer goes down from 3 sec to 0. If the
	 * timer reaches 0 or the user presses enter the world changes to
	 * WORLD_STATE_TURN_INIT
	 */
	public static final int WORLD_STATE_ENTERING_COMMAND = 0;
	/*
	 * The world gets the actions from the users, executes the AI to get the
	 * next action and gets the actions for other non playable actors (bullets).
	 * These actions are translated to movements (atomic actions, for example,
	 * the action RUN can be translated into two movements WALK performed in
	 * sucesive movement loops) The state changes to WORLD_STATE_MOVEMENT_INIT
	 */
	public static final int WORLD_STATE_TURN_INIT = 1;
	/*
	 * The world send to every actor it's next atomic action. The actors perform
	 * their action until finish (then they update a flag). When the movement
	 * has finished, the world steps to WORLD_STATE_MOVEMENT_END
	 */
	public static final int WORLD_STATE_MOVEMENT_INIT = 2;
	/*
	 * Cleaning (dispose used bullets, for example). If there are actions to be
	 * performed in this turn the world goes to WORLD_STATE_MOVEMENT_INIT. If
	 * not it goes to WORLD_STATE_TURN_END.
	 */
	public static final int WORLD_STATE_MOVEMENT_END = 3;

	/*
	 * End turn tasks: respawning, update things that change per turn (powerups)
	 * Go back to WORLD_STATE_ENTERING_COMMAND.
	 */
	public static final int WORLD_STATE_TURN_END = 4;

    // Screen values
    public static final int WAIT_TIME = 10;

    // Text processor
    public static final int NUMBEROFLINES = 4;
    public static final int LINELENGTH = 89;
    public static final String ERRORS[] = {
        "Wrong!  You cheating scum!",
        "And you call yourself a Rocket Scientist!",
        "No soap, honkie-lips.",
        "Where did you learn to type?",
        "Are you on drugs?",
        "My pet ferret can type better than you!",
        "You type like I drive.",
        "Do you think like you type?",
        "Your mind just hasn't been the same since the electro-shock, has it?",
        "Just what do you think you're doing Dave?",
        "It can only be attributed to human error.",
        "That's something I cannot allow to happen.",
        "My mind is going. I can feel it.",
        "Sorry about this, I know it's a bit silly.",
        "Take a stress pill and think things over.",
        "This mission is too important for me to allow you to jeopardize it.",
        "I feel much better now."
    };
	
}
