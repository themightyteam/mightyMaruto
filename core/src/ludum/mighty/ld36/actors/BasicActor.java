package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public interface BasicActor {
    void move(int x,int y);
    void shoot(DefaultValues.RELATIVE_DIRECTIONS direction);
    void pickup(Powerup powerup);
    void drop(String powerup);
    void rotate(DefaultValues.RELATIVE_ROTATIONS rotation);
    void setname(String name);
    void punch(DefaultValues.RELATIVE_DIRECTIONS direction);

    String getname();
    int getlife();
    int getspeed();
    int getpunch();
    boolean getvisibility();
    DefaultValues.ABSOLUTE_DIRECTIONS getfacing();
}
