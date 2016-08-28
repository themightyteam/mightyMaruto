package ludum.mighty.ld36.actors;

import java.util.ArrayList;

import ludum.mighty.ld36.actions.Action;
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
    int setlife(int life);
    int getspeed();
    int getpunch();
    boolean getvisibility();
    DefaultValues.ABSOLUTE_DIRECTIONS getfacing();
    

    void updateMovementList(ArrayList<Action> movementList);
    
    Action getNextAction();


	boolean isPlayable();
	void setPlayable(boolean playable);

	// getters and setters for position of the actor in the tilemap (the setter
	// also updates the global position of the Actor in the stage)
	int getTilePosX();

	void setTilePosX(int newPos);

	int getTilePosY();

	void setTilePosY(int newPos);
	
	
}
