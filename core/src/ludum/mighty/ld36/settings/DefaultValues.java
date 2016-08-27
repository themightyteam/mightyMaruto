package ludum.mighty.ld36.settings;

/**
 * Created by dchaves on 27/08/16.
 */
public class DefaultValues {
    // Default values for Actors
    public static final String ACTOR_NAME = "Maruto";
    public static final int ACTOR_LIFE = 100;
    public static final int ACTOR_SPEED = 1;
    public static final int ACTOR_PUNCH_DAMAGE = 1;
    public static final boolean ACTOR_VISIBILITY = true;
    public static final int ACTOR_MAX_POWERUPS = 5;
    public static final ABSOLUTE_DIRECTIONS ACTOR_DEFAULT_FACING = ABSOLUTE_DIRECTIONS.SOUTH;

    // Default values for Powerups
    public static final boolean POWERUP_CAN_BE_DROPPED = true;

    // Enumerated values
    public enum ABSOLUTE_DIRECTIONS {NORTH, SOUTH, EAST, WEST}
    public enum RELATIVE_ROTATIONS {RIGHT, LEFT}
    public enum RELATIVE_DIRECTIONS {FORWARD, BACKWADS, RIGHT, LEFT}
    public enum ACTIONS {WALK, MOONWALK, RUN, PUNCH, STOP, HELP, ROTATE, DROP, SHOOT}
    public enum POWERUPS {ARRRGGGHHH, YENDOR, CHOCO, GRENADE, RANDOM}

    // World constants
    public static final int TILESIZE = 32;

	// World state
	public static final int WORLD_STATE_ENTERING_COMMAND = 0;
	public static final int WORLD_STATE_ACTION = 1;

    // Screen values
    public static final int WAIT_TIME = 10;

    // Text processor
    public static final int NUMBEROFLINES = 4;
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
