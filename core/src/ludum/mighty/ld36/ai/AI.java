package ludum.mighty.ld36.ai;

import java.util.Random;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.actors.Actor_Powerup;
import ludum.mighty.ld36.actors.BasicMaruto;
import ludum.mighty.ld36.actors.EvilMaruto;
import ludum.mighty.ld36.actors.ItemBlackBox;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.world.MightyWorld;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AI {

	private MightyWorld world;
	private Random randGenerator;

	public AI(MightyWorld w) {
		this.world = w;
		this.randGenerator = new Random();
	}

	public void updateActors() {
		Array<Actor> actorList = this.world.getStage().getActors();

		for (int i = 0; i < actorList.size; i++){
			Actor actor = actorList.get(i);
			if (actor instanceof EvilMaruto) {
				EvilMaruto myActor = (EvilMaruto) actor;

				// some rules for the noplayer player:

				// see if we are near somebody interesting
				Array<BasicMaruto> nearList = getPlayersTouching(myActor,
						actorList);
				if (nearList != null) {
					DefaultValues.ABSOLUTE_DIRECTIONS direction = myActor
							.getFacing();
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.EAST) {
						if (isSomebodyOnTheRight(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheTop(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheBottom(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
					}
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.WEST) {
						if (isSomebodyOnTheLeft(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheTop(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheBottom(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
					}
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.NORTH) {
						if (isSomebodyOnTheTop(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheLeft(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheRight(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
					}
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH) {
						if (isSomebodyOnTheBottom(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheRight(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
						if (isSomebodyOnTheLeft(myActor, nearList)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
					}
				}

				// see if we are near something interesting to get
				Array<Actor_Powerup> nearList2 = getSomethingDesirableTouching(
						myActor, actorList);
				if (nearList2 != null) {
					DefaultValues.ABSOLUTE_DIRECTIONS direction = myActor
							.getFacing();
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.EAST) {
						if (isSomethingOnTheRight(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheTop(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheBottom(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
					}
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.WEST) {
						if (isSomethingOnTheLeft(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheTop(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheBottom(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
					}
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.NORTH) {
						if (isSomethingOnTheTop(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheLeft(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheRight(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
					}
					if (direction == DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH) {
						if (isSomethingOnTheBottom(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.SHOOT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheRight(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.LEFT));
							continue; // next actor, this one has finished
						}
						if (isSomethingOnTheLeft(myActor, nearList2)) {
							myActor.setNextAction(new Action(
									DefaultValues.ACTIONS.TURN,
									DefaultValues.RELATIVE_ROTATIONS.RIGHT));
							continue; // next actor, this one has finished
						}
					}
				}

				// do whatever
				int r = this.randGenerator.nextInt(10);
				switch (r) {
				case 0:
					myActor.setNextAction(new Action(
							DefaultValues.ACTIONS.MOONWALK));
					break;
				case 1:
					myActor.setNextAction(new Action(DefaultValues.ACTIONS.RUN));
					break;
				case 2:
					myActor.setNextAction(new Action(DefaultValues.ACTIONS.PICK));
					break;
				case 3:
					myActor.setNextAction(new Action(
							DefaultValues.ACTIONS.TURN,
							DefaultValues.RELATIVE_ROTATIONS.LEFT));
					break;
				case 4:
					myActor.setNextAction(new Action(
							DefaultValues.ACTIONS.TURN,
							DefaultValues.RELATIVE_ROTATIONS.RIGHT));
					break;
				case 5:
					myActor.setNextAction(new Action(
							DefaultValues.ACTIONS.SHOOT));
					break;
				default:
					myActor.setNextAction(new Action(DefaultValues.ACTIONS.WALK));
					break;
				}
			}
		}
	}



	private boolean isSomebodyOnTheLeft(EvilMaruto a,
			Array<BasicMaruto> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			BasicMaruto ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX - 1) & (ba.getTilePosY() == tileY)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSomethingOnTheLeft(EvilMaruto a,
			Array<Actor_Powerup> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			Actor_Powerup ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX - 1) & (ba.getTilePosY() == tileY)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSomebodyOnTheRight(EvilMaruto a,
			Array<BasicMaruto> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			BasicMaruto ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX + 1) & (ba.getTilePosY() == tileY)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSomethingOnTheRight(EvilMaruto a,
			Array<Actor_Powerup> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			Actor_Powerup ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX + 1) & (ba.getTilePosY() == tileY)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSomebodyOnTheTop(EvilMaruto a, Array<BasicMaruto> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			BasicMaruto ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX + 1)
					& (ba.getTilePosY() == tileY + 1)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSomethingOnTheTop(EvilMaruto a,
			Array<Actor_Powerup> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			Actor_Powerup ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX + 1)
					& (ba.getTilePosY() == tileY + 1)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSomebodyOnTheBottom(EvilMaruto a,
			Array<BasicMaruto> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			BasicMaruto ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX + 1)
					& (ba.getTilePosY() == tileY - 1)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSomethingOnTheBottom(EvilMaruto a,
			Array<Actor_Powerup> nearList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		for (int i = 0; i < nearList.size; i++) {
			Actor_Powerup ba = nearList.get(i);
			if ((ba.getTilePosX() == tileX + 1)
					& (ba.getTilePosY() == tileY - 1)) {
				return true;
			}
		}
		return false;
	}

	private Array<BasicMaruto> getPlayersTouching(EvilMaruto a,
			Array<Actor> actorList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		Array<BasicMaruto> res = new Array<BasicMaruto>();

		for (int i = 0; i < actorList.size; i++) {
			Actor actor = actorList.get(i);
			if (actor instanceof BasicMaruto) {
				BasicMaruto ba = (BasicMaruto) actor;
				if (((ba.getTilePosX() == tileX + 1) & (ba.getTilePosY() == tileY))
						| ((ba.getTilePosX() == tileX - 1) & (ba.getTilePosY() == tileY))
						| ((ba.getTilePosX() == tileX) & (ba.getTilePosY() == tileY + 1))
						| ((ba.getTilePosX() == tileX) & (ba.getTilePosY() == tileY - 1))) {
					res.add(ba);
				}
			}
		}
		return null;
	}

	private Array<Actor_Powerup> getSomethingDesirableTouching(EvilMaruto a,
			Array<Actor> actorList) {
		int tileX = a.getTilePosX();
		int tileY = a.getTilePosY();

		Array<Actor_Powerup> res = new Array<Actor_Powerup>();

		for (int i = 0; i < actorList.size; i++) {
			Actor actor = actorList.get(i);
			if (actor instanceof ItemBlackBox) {
				Actor_Powerup ba = (Actor_Powerup) actor;
				if (((ba.getTilePosX() == tileX + 1) & (ba.getTilePosY() == tileY))
						| ((ba.getTilePosX() == tileX - 1) & (ba.getTilePosY() == tileY))
						| ((ba.getTilePosX() == tileX) & (ba.getTilePosY() == tileY + 1))
						| ((ba.getTilePosX() == tileX) & (ba.getTilePosY() == tileY - 1))) {
					res.add(ba);
				}
			}
		}

		return null;
	}

}
