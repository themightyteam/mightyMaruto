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
}
