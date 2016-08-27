package ludum.mighty.ld36.settings;

/**
 * Created by dchaves on 27/08/16.
 */
public class DefaultValues {
    //Default values for Actors
    public static final String ACTOR_NAME = "Maruto";
    public static final int ACTOR_LIFE = 100;
    public static final int ACTOR_SPEED = 1;
    public static final int ACTOR_PUNCH_DAMAGE = 1;
    public static final boolean ACTOR_VISIBILITY = true;
    public static final int ACTOR_MAX_POWERUPS = 5;

    //Default values for Powerups
    public static final boolean POWERUP_CAN_BE_DROPPED = true;

    // Enumerated values
    public enum ABSOLUTE_DIRECTIONS {NORTH, SOUTH, EAST, WEST}
    public enum RELATIVE_ROTATIONS {RIGHT, LEFT}
    public enum RELATIVE_DIRECTIONS {FORWARD, BACKWADS, RIGHT, LEFT}
}
