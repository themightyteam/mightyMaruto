package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Action {
    private int movex;
    private int movey;
    private DefaultValues.RELATIVE_ROTATIONS rotate;
    private DefaultValues.ABSOLUTE_DIRECTIONS shoot_direction;

    // Disable default constructor
    private Action(){}

    // Action constructor
    public Action(int movex, int movey, DefaultValues.RELATIVE_ROTATIONS rotate, DefaultValues.ABSOLUTE_DIRECTIONS shoot) {

    }

    //Execute action on an actor
    public void applyTo(BasicActor actor) {

    }
}
