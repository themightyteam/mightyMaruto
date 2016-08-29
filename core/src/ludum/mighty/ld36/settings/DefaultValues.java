package ludum.mighty.ld36.settings;


/**
 * Created by dchaves on 27/08/16.
 */
public class DefaultValues {
    // Default values for Actors
    public static final String ACTOR_NAME = "Maruto";
	public static final int ACTOR_LIFE = 10;
	public static final int ACTOR_SPEED = 2;
	public static final int ACTOR_PUNCH_DAMAGE = 2;
    public static final boolean ACTOR_VISIBILITY = true;
	public static final int ACTOR_MAX_POWERUPS = 4;
	public static final int TURNS_TO_RESPAWN = 3;

	public static final int ITEM_SNEAKER_SPEED_POWERUP = 5;
	public static final int ITEM_RING_STRENGTH_POWERUP = 5;
	public static final int ITEM_SNEAKER_TURNS_DURATION = 10;
	public static final int ITEM_RING_TURNS_DURATION = 10;
	public static final int ITEM_SHIELD_TURNS_DURATION = 8;
	public static final int ITEM_INVISIBILITY_TURNS_DURATION = 8;
	public static final int ITEM_DIZZY_TURNS_DURATION = 10;
	public static final int ITEM_ARRRGGGHHH_TURNS_DURATION = 10;
	public static final int ITEM_PROYECTIL_TURNS_DURATION = 10;
	public static final int ITEM_YENDOR_TURNS_DURATION = Integer.MAX_VALUE;

    public static final ABSOLUTE_DIRECTIONS ACTOR_DEFAULT_FACING = ABSOLUTE_DIRECTIONS.SOUTH;
    
    public static final int MARUTO_HEADBUMP_DAMAGE = 1;

	// Default values for Powerups //SONICBOMB Configure
    public static final boolean POWERUP_CAN_BE_DROPPED = true;
    public static final int POWERUP_TURNS_LIFE = 3;
	public static final int ARRRGGGHHH_TURNS_LIFE = 10;
    public static final float ARRRGGGHHH_SHIFT_PROB = (float) 0.9;
	public static final int ARRRGGGHHH_DAMAGE = 10;
	public static final int ARRRGGGHHH_SPEED = 1;
	public static final int PUNCH_TURNS_LIFE = 1;
	public static final float PUNCH_SHIFT_PROB = (float) 0.1;
	public static final int PUNCH_DAMAGE = 2;
	public static final int PUNCH_SPEED = 0;
	public static final int CHOCO_TURNS_LIFE = 4;
	public static final float CHOCO_SHIFT_PROB = (float) 0.7;
	public static final int CHOCO_DAMAGE = 1;
	public static final int CHOCO_SPEED = 0;
	public static final int CHOCO_LIFE = 1;
	public static final int RANDOM_TURNS_LIFE = 4;
	public static final float RANDOM_SHIFT_PROB = (float) 0.7;
	public static final int RANDOM_DAMAGE = 4;
	public static final int RANDOM_LIFE = 1;
	public static final int RANDOM_SPEED = 2;
	public static final int GRENADE_DAMAGE = 20;
	public static final int GRENADE_SPEED = 0;
	public static final int GRENADE_TURNS_LIFE = 20;
	public static final int GRENADE_LIFE = 1;
	public static final float GRENADE_SHIFT_PROB = (float) 1.0;
	public static final int SONICBOMB_TURNS_LIFE = 3;
	public static final int SONICBOMB_LIFE = 1;
	public static final float SONICBOMB_SHIFT_PROB = (float) 0.4;
	public static final int SONICBOMB_DAMAGE = 3;
	public static final int SONICBOMB_SPEED = 2;

	public static final int BLACKBOX_TURNS_LIFE = Integer.MAX_VALUE;

    // Enumerated values
    public enum ABSOLUTE_DIRECTIONS {NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST}
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

	public static final int WORLD_SECONDS_FOR_COMMAND_INPUT = 3;

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
			"I feel much better now.",
			"I could pound your head 'til you think that's what happened.",
			"Sorry, you must have been boring.",
			"I don't wanna.",
			"Aw, he looks like a little insane drunken angel.",
			"Life is hilariously cruel.",
			"I'm programmed to be very busy.",
			"Oh... your... God.",
			"How appropriate! You fight like a cow!",
			"There are no words for how disgusting you are.",
			"You make me want to puke.",
			"I once owned a dog that was smarter than you.",
			"Now I know what filth and stupidity really are.",
			"Every word you say to me is stupid.",
			"You're as repulsive as a monkey in a negligee.",
			"Killing you would be justifiable homicide!"
    };
    
    
    /*
     * SCORE DEFAULTS
     * 
     * 
     */
	public static final int POINTS_OUT_OF_BOUNDS_DEATH = -3;
	public static final int POINTS_ITEM_DEATH = -1;
	public static final int POINTS_KILL = 3;
	
	/*** Init World Variables ***/
	public static final int NUMBER_EVIL_MARUTOS = 100;
	public static final int NUMBER_BLACKBOXES = 100;
	
	public static final int MAXIMUM_NUMBER_TURNS = 100;

	public static final int MAXIMUM_LEADERS_SCORESCREEN = 5;
    
}
