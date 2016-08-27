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

    // Enumerated values
    public enum DIRECTIONS {NORTH, SHOUTH, EAST, WEST}
    public enum ROTATIONS {RIGHT, LEFT}
}
