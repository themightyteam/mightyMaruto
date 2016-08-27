package ludum.mighty.ld36.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import ludum.mighty.ld36.settings.DefaultValues;

/**
 * Created by dchaves on 27/08/16.
 */
public class BasicMaruto extends Actor implements BasicActor {
    private String name = DefaultValues.ACTOR_NAME;
    private int life = DefaultValues.ACTOR_LIFE;
    private int punch = DefaultValues.ACTOR_PUNCH_DAMAGE;
    private int speed = DefaultValues.ACTOR_SPEED;
    private boolean visibility = DefaultValues.ACTOR_VISIBILITY;

    @Override
    public void move(int x, int y) {

    }

    @Override
    public void shoot(DefaultValues.DIRECTIONS direction) {

    }

    @Override
    public void pickup(Powerup powerup) {

    }

    @Override
    public void rotate(DefaultValues.ROTATIONS rotation) {

    }


}
