package ludum.mighty.ld36.ai;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.actors.EvilMaruto;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.world.MightyWorld;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AI {

	MightyWorld world;

	public AI(MightyWorld w) {
		this.world = w;
	}

	public void updateActors() {
		Array<Actor> actorList = this.world.getStage().getActors();

		for (Actor actor : actorList) {
			if (actor instanceof EvilMaruto) {
				EvilMaruto myActor = (EvilMaruto) actor;

				myActor.setNextAction(new Action(DefaultValues.ACTIONS.WALK));

			}
		}
	}
}
