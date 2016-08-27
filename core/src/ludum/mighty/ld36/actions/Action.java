package ludum.mighty.ld36.actions;

import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Action {
    private DefaultValues.ACTIONS type;
    private DefaultValues.RELATIVE_DIRECTIONS direction;
    private DefaultValues.POWERUPS powerup;

    // Disable default constructor
    private Action(){}

    // Constructor for actions without parameters
    public Action(DefaultValues.ACTIONS type) throws Exception {
        if ((type == DefaultValues.ACTIONS.WALK) ||
                (type == DefaultValues.ACTIONS.MOONWALK) ||
                (type == DefaultValues.ACTIONS.RUN) ||
                (type == DefaultValues.ACTIONS.PUNCH) ||
                (type == DefaultValues.ACTIONS.STOP) ||
                (type == DefaultValues.ACTIONS.HELP)) {
            this.type = type;
        } else {
            throw new Exception("Action error");
        }
    }

    // Constructor for actions with direction parameters
    public Action(DefaultValues.ACTIONS type, DefaultValues.RELATIVE_DIRECTIONS param) throws Exception {
        if (type != DefaultValues.ACTIONS.ROTATE) {
            this.type = type;
            this.direction = param;
        } else {
            throw new Exception("Action error");
        }
    }

    // Constructor for actions with powerup parameters
    public Action(DefaultValues.ACTIONS type, DefaultValues.POWERUPS powerup) {

    }

    // Get type of action
    public DefaultValues.ACTIONS gettype() {
        return this.type;
    }

    // Get relative direction of action or null if none
    public DefaultValues.RELATIVE_DIRECTIONS getdirection() {
        if ((type == DefaultValues.ACTIONS.WALK) ||
                (type == DefaultValues.ACTIONS.MOONWALK) ||
                (type == DefaultValues.ACTIONS.RUN) ||
                (type == DefaultValues.ACTIONS.PUNCH) ||
                (type == DefaultValues.ACTIONS.STOP) ||
                (type == DefaultValues.ACTIONS.HELP)) {
            return null;
        }
        return this.direction;
    }

    // Get powerup of action or null if none
    public DefaultValues.RELATIVE_DIRECTIONS getpowerup() {
        if (!((type == DefaultValues.ACTIONS.DROP) || (type == DefaultValues.ACTIONS.SHOOT))) {
            return null;
        }
        return this.direction;
    }
}
