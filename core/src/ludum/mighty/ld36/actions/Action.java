package ludum.mighty.ld36.actions;

import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Action {
    private DefaultValues.ACTIONS type;
    private DefaultValues.RELATIVE_ROTATIONS direction;
    private DefaultValues.POWERUPS powerup;

    // Disable default constructor
    private Action(){}

    // Constructor for actions without parameters
    public Action(DefaultValues.ACTIONS type) {
        if ((type == DefaultValues.ACTIONS.WALK) ||
                (type == DefaultValues.ACTIONS.MOONWALK) ||
                (type == DefaultValues.ACTIONS.RUN) ||
                (type == DefaultValues.ACTIONS.STOP) ||
                (type == DefaultValues.ACTIONS.HELP)) {
            this.type = type;
            this.direction = null;
            this.powerup = null;
        }
    }

    // Constructor for actions with direction parameters
    public Action(DefaultValues.ACTIONS type, DefaultValues.RELATIVE_ROTATIONS param) {
        if (type == DefaultValues.ACTIONS.TURN) {
            this.type = type;
            this.direction = param;
            this.powerup = null;
        }
    }

    // Constructor for actions with powerup parameters
    public Action(DefaultValues.ACTIONS type, DefaultValues.POWERUPS powerup) {
        if ((type == DefaultValues.ACTIONS.DROP) || (type == DefaultValues.ACTIONS.SHOOT)) {
            this.type = type;
            this.powerup = powerup;
            this.direction = null;
        }
    }

    // Get type of action
    public DefaultValues.ACTIONS gettype() {
        return this.type;
    }

    // Get relative direction of action or null if none
    public DefaultValues.RELATIVE_ROTATIONS getdirection() {
        if ((type == DefaultValues.ACTIONS.WALK) ||
                (type == DefaultValues.ACTIONS.MOONWALK) ||
                (type == DefaultValues.ACTIONS.RUN) ||
                (type == DefaultValues.ACTIONS.STOP) ||
                (type == DefaultValues.ACTIONS.HELP)) {
            return null;
        }
        return this.direction;
    }

    // Get powerup of action or null if none
    public DefaultValues.POWERUPS getpowerup() {
        if (!((type == DefaultValues.ACTIONS.DROP) || (type == DefaultValues.ACTIONS.SHOOT))) {
            return null;
        }
        return this.powerup;
    }

    @Override
    public String toString(){
        String result = this.type.name();
        if (powerup != null) {
            result += " " + this.powerup.name();
        }
        if (direction != null) {
            result += " " + this.direction.name();
        }
        return result;
    }
}
