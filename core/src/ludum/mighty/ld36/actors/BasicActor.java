package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public interface BasicActor {
    void move(int x,int y);
    void shoot(DefaultValues.DIRECTIONS direction);
    void pickup(Powerup powerup);
    void rotate(DefaultValues.ROTATIONS rotation);
}
