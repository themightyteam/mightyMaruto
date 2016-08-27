package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Action {
    private int movex;
    private int movey;
    private DefaultValues.ROTATIONS rotate;
    private DefaultValues.DIRECTIONS shoot_direction;

    // Disable default constructor
    private Action(){}

    // Action constructor
    public Action(int movex, int movey, DefaultValues.ROTATIONS rotate, DefaultValues.DIRECTIONS shoot) {

    }

    //Execute action on an actor
    public void applyTo(BasicActor actor) {

    }
}
