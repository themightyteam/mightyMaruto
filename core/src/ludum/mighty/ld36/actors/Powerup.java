package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class Powerup {
    private boolean canbedropped = DefaultValues.POWERUP_CAN_BE_DROPPED;
    private String name;

    public boolean candrop() {
        return this.canbedropped;
    }

    public String getname() {
        return this.name;
    }
}
