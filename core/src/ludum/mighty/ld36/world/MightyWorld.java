package ludum.mighty.ld36.world;

import java.util.ArrayList;
import java.util.Random;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.actors.Actor_Powerup;
import ludum.mighty.ld36.actors.BasicMaruto;
import ludum.mighty.ld36.actors.CommonActor;
import ludum.mighty.ld36.actors.EvilMaruto;
import ludum.mighty.ld36.actors.GoodMaruto;
import ludum.mighty.ld36.actors.Item_ARRRGGGHHH;
import ludum.mighty.ld36.actors.Item_Powerup;
import ludum.mighty.ld36.actors.Item_Punch;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.textTerminal.CommandProcessor;
import ludum.mighty.ld36.textTerminal.TextTerminal;
import ludum.mighty.ld36.timeLeftLabel.TimeLeftLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class MightyWorld {

	// The world can be in two states:
	// 1. Entering a command from the console (nobody moves)
	// 2. Actors perform this turn's actions (first the AI is updated, then the
	// mechanics of the world are calculated and translated into actors
	// actions and last the actions of the turn are displayed)
	private int currentState;

	private long timeWhenTurnStarted;
	private long timeSinceTurnStarted;

	private Stage stage;
	private TiledMap tiledMap;
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	// Rendering objects
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera cam;

	public StretchViewport sv;

	public BasicMaruto basicMaruto;

	private SpriteBatch batch;
	private TextTerminal textTerminal;
	private TimeLeftLabel timeLeftLabel;

	private CommandProcessor parser;

	boolean actionsPending = false;

	Random generator = new Random();

	// // WORLD API
	// Loads stuff like the map and initializes things
	public void init(TiledMap map) {

		this.tiledMap = map;
		MapProperties prop = this.tiledMap.getProperties();
		this.mapWidthInTiles = prop.get("width", Integer.class);
		this.mapHeightInTiles = prop.get("height", Integer.class);

		int h = Gdx.graphics.getHeight();
		// Render init

		this.mapRenderer = new OrthogonalTiledMapRenderer(map);
		this.cam = new OrthographicCamera();
		this.sv = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.cam);
		this.cam.position.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);

		batch = new SpriteBatch();

		stage = new Stage(sv);

		this.textTerminal = new TextTerminal(new Vector2(0f, 100f), Gdx.graphics.getWidth(),
				100);
		Gdx.input.setInputProcessor(this.textTerminal);

		this.timeWhenTurnStarted = TimeUtils.millis();
		this.timeSinceTurnStarted = TimeUtils.millis()
				- this.timeWhenTurnStarted;
		this.timeLeftLabel = new TimeLeftLabel(new Vector2(10f, h - 10f), 120,
				20);
		this.timeLeftLabel
				.setTimeLeft(((float) DefaultValues.WORLD_SECONDS_FOR_COMMAND_INPUT)
						- (float) this.timeSinceTurnStarted / 1000);

		this.parser = new CommandProcessor();

		// TODO: define user input here

		this.currentState = DefaultValues.WORLD_STATE_ENTERING_COMMAND;
		initPlayers();
		updatePowerUps();

		this.timeWhenTurnStarted = TimeUtils.millis();
	}

	// updates the world (this depends on, among other things, the current
	// state of the world)
	// this is called in the screen render or update
	public void update() 
	{
		//Update time here always but use TimeUtil!!!
		//If timer goes below 0 the turn is over and a new turn is started


		switch(this.currentState)
		{
		case DefaultValues.WORLD_STATE_ENTERING_COMMAND:

			this.timeSinceTurnStarted = TimeUtils.millis()
					- this.timeWhenTurnStarted;
			this.timeLeftLabel
					.setTimeLeft(((float) DefaultValues.WORLD_SECONDS_FOR_COMMAND_INPUT)
							- (float) this.timeSinceTurnStarted / 1000);

			// if something new typed we parse it and load to the actor
			if (this.textTerminal.isThereALineNotSent()) {
				this.textTerminal.addLine(this.parser.next(this.textTerminal
						.getOldestUnprocessedLine()));
				this.textTerminal.disable();

				this.basicMaruto.setNextAction(this.parser.getNextAction());

				this.currentState = DefaultValues.WORLD_STATE_TURN_INIT;
				this.timeLeftLabel.setTimeLeft(0);
			}

			if (this.timeSinceTurnStarted > DefaultValues.WORLD_SECONDS_FOR_COMMAND_INPUT * 1000) {
				this.currentState = DefaultValues.WORLD_STATE_TURN_INIT;
				this.timeLeftLabel.setTimeLeft(0);
				this.textTerminal.disable();
			}
			break;
		case DefaultValues.WORLD_STATE_TURN_INIT:
			System.out.println("WORLD_STATE_TURN_INIT");
			this.checkTurnUpdate();
			this.currentState = DefaultValues.WORLD_STATE_MOVEMENT_INIT;
			break;
		case DefaultValues.WORLD_STATE_TURN_END:
			System.out.println("WORLD_STATE_TURN_END");
			//TODO: Doing Nothing?
			//Update turn-based items, respawn, etc
			this.finishTurn();

			this.textTerminal.enable();
			this.timeWhenTurnStarted = TimeUtils.millis();
			this.currentState = DefaultValues.WORLD_STATE_ENTERING_COMMAND;

			break;
		case DefaultValues.WORLD_STATE_MOVEMENT_INIT:
			System.out.println("WORLD_STATE_MOVEMENT_INIT");

			// if (this.isMovementStepFinished())
			// {
				System.out.println("WORLD_STATE_FIRST_MOVE");

			this.actionsPending = this.nextMovementUpdate();

				this.currentState = DefaultValues.WORLD_STATE_MOVEMENT_END;


			// } else
			// System.out.println("NOT ALL FINISHED");
			break;
		case DefaultValues.WORLD_STATE_MOVEMENT_END:
			System.out.println("WORLD_STATE_MOVEMENT_END");

			// Checking Players
			for (Actor actor : this.stage.getActors()) {
				if (actor instanceof BasicMaruto) {
					BasicMaruto myMaruto = (BasicMaruto) actor;

					System.out.println("X: "
							+ Float.toString(actor.getX())
							+ "  TILED X: "
							+ Integer.toString(((BasicMaruto) actor)
									.getTilePosX())
							+ "   Y: "
							+ Float.toString(actor.getY())
							+ "  TILED Y: "
							+ Integer.toString(((BasicMaruto) actor)
									.getTilePosY())

					);

				}

			}

			//Wait till movements are finished
			// if (this.isMovementStepFinished())
			// {
				this.finishMovement();
				this.checkRespawn();
				this.updateInventory();

			if (this.actionsPending)

				this.currentState = DefaultValues.WORLD_STATE_MOVEMENT_INIT;

			else
				this.currentState = DefaultValues.WORLD_STATE_TURN_END;
			// }

			break;
		}


		/**

		if (this.currentState == DefaultValues.WORLD_STATE_ENTERING_COMMAND) {
			// TODO: update user input here

			this.timeLeftLabel
					.setTimeLeft(this.timeLeftLabel.getTimeLeft() - 0.001f);
		} else if (this.currentState == DefaultValues.WORLD_STATE_ACTION) {

		}


		//this.stage.act(Gdx.graphics.getDeltaTime());
		 * 
		 * 
		 */
	}

	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updatePowerUps();
		// basicMaruto.checkAction();

		this.cam.position.x = basicMaruto.getX();
		this.cam.position.y = basicMaruto.getY();
		this.cam.update();
		this.mapRenderer.setView(this.cam);

		this.mapRenderer.render();
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		this.textTerminal.render(batch);
		this.timeLeftLabel.render(batch);
	}

	// // END OF WORLD API

	// Creates all players
	private void initPlayers() {
		// Add playable player
		basicMaruto = new GoodMaruto();

		// set the xy for the tiles and stage position
		//TODO: set this position randomly
		basicMaruto.setTilePosX(24);
		basicMaruto.setTilePosY(24);
		this.stage.addActor(basicMaruto);

		EvilMaruto eM = new EvilMaruto();
		eM.setTilePosX(26);
		eM.setTilePosY(24);
		this.stage.addActor(eM);

		eM = new EvilMaruto();
		eM.setTilePosX(24);
		eM.setTilePosY(26);
		this.stage.addActor(eM);

		eM = new EvilMaruto();
		eM.setTilePosX(25);
		eM.setTilePosY(25);
		this.stage.addActor(eM);

		// TODO: Add rest of players
	}

	// Checks all present power up items on the map and spawns new power ups if
	// necessary
	private void updatePowerUps() {

	}

	public void initTurnUpdate()
	{

	}


	public void checkTurnUpdate()
	{
		//Grab all actions
		//Grab Movements first iterations of all the actors	
		Array<Actor> actorList = this.stage.getActors();

		for (Actor actor : actorList)
		{
			if (actor instanceof CommonActor)
			{
				CommonActor myActor = (CommonActor) actor;

				Action action =  myActor.getNextAction();

				//Pick movements from actions
				ArrayList<Action> movementList = this.getMovementsFromAction(action, myActor.getspeed(), actor instanceof BasicMaruto);

				//Store movement list in actor
				myActor.updateMovementList(movementList);
			}
		}

	}


	public boolean isMovementStepFinished()
	{
		Array<Actor> actorList = this.stage.getActors();
		//Checking all actors (no unfinished movement)

		boolean allMovementsFinished = true;
		for (Actor actor : actorList)
		{
			CommonActor myActor = (CommonActor) actor;

			if (myActor.isMoveFlag())
			{
				System.out.println("ACTOR IS A MOTHERFUCKER");
				allMovementsFinished = false;
				return allMovementsFinished;

			}
		}

		return allMovementsFinished;
	}

	public boolean nextMovementUpdate()
	{

		this.actionsPending = false;

		Array<Actor> actorList = this.stage.getActors();
		//Checking all actors (no unfinished movement)

		// This list is to store new actors created in this movement
		Array<Actor> newActorList = this.stage.getActors();

		//Checking powerups

		for (int i = 0; i < actorList.size; i++)
 {
			Actor actor = actorList.get(i);

			if (actor instanceof Actor_Powerup)
			{
				Actor_Powerup mypowerup = (Actor_Powerup) actor;

				if (mypowerup.getMovementList().size() > 0)
				{

					Action movement = mypowerup.getMovementList().remove(0);


					mypowerup.doMovement(movement);

					//Check action outcome

					for (int j = 0; j < actorList.size; j++)
					{
						Actor nextActor = actorList.get(j);
						if (i != j)
						{

							CommonActor otherActor = (CommonActor) nextActor;

							if ((otherActor.getTilePosX() == mypowerup.getTilePosX()) &&
									(otherActor.getTilePosY() == mypowerup.getTilePosY()))
							{


								//Update life

								if (otherActor.isCanBeHit())
									otherActor.setlife(otherActor.getlife() - mypowerup.getpunch());

								//life of my powerup is also diminished by 1 (losing force after collision)
								mypowerup.setlife(mypowerup.getlife() - 1);

								//If the other actor is death remove its actions and add a DEATH
								if (otherActor.getlife() <= 0 )
								{
									otherActor.getMovementList().clear();
									otherActor.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
								}
								else
								{

									if (otherActor.isCanBeHit())
									{
										//Add a hit or a shifted hit
										otherActor.getMovementList().clear();

										float randomProb = this.generator.nextFloat();

										if (nextActor instanceof BasicMaruto)
										{
											if (randomProb < mypowerup.getShiftProbability())
												otherActor.getMovementList().add(new Action(DefaultValues.ACTIONS.SHIFT_HIT));
											else 
												otherActor.getMovementList().add(new Action(DefaultValues.ACTIONS.HIT));

										}
									}
								}

								if (mypowerup.getlife() < 0 )
								{
									mypowerup.getMovementList().clear();
									mypowerup.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
								}
							}
						}
					}

					actionsPending=true;
				}
			}
		}

		for (int i = 0; i < actorList.size; i++)
		{

			Actor actor = actorList.get(i);

			//TODO
			if (actor instanceof BasicMaruto)
			{
				BasicMaruto myMaruto = (BasicMaruto) actor;

				if (myMaruto.getMovementList().size() > 0)
				{
					Action movement = myMaruto.getMovementList().remove(0);

					// Check changes
					if (movement.gettype() == DefaultValues.ACTIONS.SHOOT) {

						CommonActor newActor;

						switch (movement.getpowerup()) {

						case ARRRGGGHHH:
							newActor = new Item_ARRRGGGHHH();

							// Set x-y position of item (initial position)
							newActor.setTilePosX(this
									.obtainItemSpawnX(myMaruto));
							newActor.setTilePosY(this
									.obtainItemSpawnY(myMaruto));
							newActorList.add(newActor);

							break;
						case CHOCO:

							break;
						case SONICBOMB:
							break;
						case GRENADE:
							break;
						case PUNCH:
							newActor = new Item_Punch();

							// Set x-y position of item (initial position)
							newActor.setTilePosX(this
									.obtainItemSpawnX(myMaruto));
							newActor.setTilePosY(this
									.obtainItemSpawnY(myMaruto));
							newActorList.add(newActor);
							newActorList.add(newActor);
							break;
						case DIAG_SONICBOMB:
							break;
						case RANDOM:
							break;

						default:
							break;
						}

					} else if (movement.gettype() == DefaultValues.ACTIONS.DROP) {

						switch (movement.getpowerup()) {
						case ARRRGGGHHH:
							myMaruto.drop(DefaultValues.POWERUPS.ARRRGGGHHH
									.toString());
							break;
						case CHOCO:
							myMaruto.drop(DefaultValues.POWERUPS.CHOCO
									.toString());
							break;

						case SONICBOMB:
							myMaruto.drop(DefaultValues.POWERUPS.SONICBOMB
									.toString());
							break;
						case GRENADE:
							myMaruto.drop(DefaultValues.POWERUPS.GRENADE
									.toString());
							break;
						case RING:
							myMaruto.drop(DefaultValues.POWERUPS.RING
									.toString());
							break;
						case YENDOR:
							myMaruto.drop(DefaultValues.POWERUPS.YENDOR
									.toString());
							break;
						case SHIELD:
							myMaruto.drop(DefaultValues.POWERUPS.SHIELD
									.toString());
							break;
						case SNEAKERS:
							myMaruto.drop(DefaultValues.POWERUPS.SNEAKERS
									.toString());
							break;
						case INVISIBILITY:
							myMaruto.drop(DefaultValues.POWERUPS.INVISIBILITY
									.toString());
							break;
						case DIAG_SONICBOMB:
							myMaruto.drop(DefaultValues.POWERUPS.DIAG_SONICBOMB
									.toString());
							break;

						default:
							break;
						}

					} else if (movement.gettype() == DefaultValues.ACTIONS.PICK) {

						switch (movement.getpowerup()) {
						case RING:

							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.RING,
									DefaultValues.ITEM_RING_TURNS_DURATION, 0,
									DefaultValues.ITEM_RING_STRENGTH_POWERUP,
									false, false, false));
							break;
						case SHIELD:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.SHIELD,
									DefaultValues.ITEM_SHIELD_TURNS_DURATION,
									0, 0, false, false, true));

							break;
						case INVISIBILITY:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.INVISIBILITY,
									DefaultValues.ITEM_INVISIBILITY_TURNS_DURATION,
									0,
									0, true, false, false));

							break;
						case DIZZY:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.DIZZY,
									DefaultValues.ITEM_DIZZY_TURNS_DURATION, 0,
									0,
									false, true, false));
							break;
						case SNEAKERS:

							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.SNEAKERS,
									DefaultValues.ITEM_SNEAKER_TURNS_DURATION,
									DefaultValues.ITEM_SNEAKER_SPEED_POWERUP,
									0, false, false, false));

							break;

						case ARRRGGGHHH:

							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.ARRRGGGHHH,
									DefaultValues.ITEM_ARRRGGGHHH_TURNS_DURATION,
									0, 0, false, false, false));

							break;
						case YENDOR:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.YENDOR,
									DefaultValues.ITEM_YENDOR_TURNS_DURATION,
									0, 0, false, false, false));

							break;
						case CHOCO:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.CHOCO,
									DefaultValues.ITEM_PROYECTIL_TURNS_DURATION,
									0, 0, false, false, false));

							break;
						case GRENADE:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.GRENADE,
									DefaultValues.ITEM_PROYECTIL_TURNS_DURATION,
									0, 0, false, false, false));

							break;
						case SONICBOMB:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.SONICBOMB,
									DefaultValues.ITEM_PROYECTIL_TURNS_DURATION,
									0, 0, false, false, false));

							break;
						case DIAG_SONICBOMB:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.DIAG_SONICBOMB,
									DefaultValues.ITEM_PROYECTIL_TURNS_DURATION,
									0, 0, false, false, false));

							break;
						case BLACKBOX:

							Action nextAction = this.obtainItemInBox();
							// Grab the item in the next movement
							myMaruto.getMovementList().add(0, nextAction);

							break;

						default:
							break;
						}
					}

					System.out.println("MOVE" + movement.gettype().toString());

					myMaruto.doMovement(movement);

					//Check action outcome

					for (int j = 0; j < actorList.size; j++)
					{
						Actor nextActor = actorList.get(j);
						if (i != j)
						{
							if (nextActor instanceof Actor_Powerup)
							{
								CommonActor otherActor = (CommonActor) nextActor;

								if ((otherActor.getTilePosX() == myMaruto.getTilePosX()) &&
										(otherActor.getTilePosY() == myMaruto.getTilePosY()))
								{


									//Update life
									otherActor.setlife(otherActor.getlife() - 1);

									//life of my powerup is also diminished by 1 (losing force after collision)
									otherActor.setlife(otherActor.getlife() - 1);
									if (myMaruto.isCanBeHit())
										myMaruto.setlife( myMaruto.getlife() - otherActor.getpunch());

									//Check powerup life
									if (otherActor.getlife() < 0)
									{
										otherActor.getMovementList().clear();
										otherActor.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
									}

									//Check my maruto life

									if (myMaruto.getlife() <= 0 )
									{
										myMaruto.getMovementList().clear();
										myMaruto.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
									}
									else
									{
										float randomProb = this.generator.nextFloat();
										myMaruto.getMovementList().clear();
										if (randomProb < myMaruto.getShiftProbability())
											myMaruto.getMovementList().add(new Action(DefaultValues.ACTIONS.SHIFT_HIT));
										else 
											myMaruto.getMovementList().add(new Action(DefaultValues.ACTIONS.HIT));
									}

								}

							}
							else if (nextActor instanceof BasicMaruto)
							{
								CommonActor otherActor = (CommonActor) nextActor;

								if ((otherActor.getTilePosX() == myMaruto
										.getTilePosX())
										&& (otherActor.getTilePosY() == myMaruto
												.getTilePosY())) {

									// Maruto Against Maruto (a headbump)

									myMaruto.setlife(myMaruto.getlife()
											- DefaultValues.MARUTO_HEADBUMP_DAMAGE);

									if (myMaruto.getlife() <= 0) {
										myMaruto.getMovementList().clear();
										myMaruto.getMovementList()
												.add(new Action(
														DefaultValues.ACTIONS.DEATH));
									} else {
										myMaruto.getMovementList().clear();
										myMaruto.getMovementList()
												.add(new Action(
														DefaultValues.ACTIONS.SHIFT_HIT));
									}

								}

							}
						}
					}
				}

			}

		}

		// Finally Add actors to scene
		for (Actor actor : newActorList) {
			this.stage.addActor(actor);
		}

		return actionsPending;
	}

	/**
	 * 
	 * 
	 * - It deletes power-ups after their collide with something and they do not have life remaining
	 * (typically a power-up will have 1 value of live, least the arrrggghhh monster which is invincible )
	 * 
	 */
	public void finishMovement()
	{
		Array<Actor> actorList = this.stage.getActors();
		//Checking all actors (no unfinished movement)

		Array<Actor> newActorList = new Array<Actor>();

		//Delete actors 
		for (Actor actor : actorList)
		{
			if (actor instanceof Actor_Powerup)
			{
				Actor_Powerup mypowerup = (Actor_Powerup) actor;

				if (mypowerup.getlife() > 0 ) 
				{
					newActorList.add(actor);
				}
			}
			else
			{
				newActorList.add(actor);
			}
		}

		this.stage.getActors().clear();

		for (Actor actor : newActorList)
		{
			this.stage.addActor(actor);
		}

	}


	public void checkRespawn()
	{
		Array<Actor> actorList = this.stage.getActors();
		//Checking all actors (no unfinished movement)

		//Checking Players
		for (Actor actor : actorList)
		{
			if (actor instanceof BasicMaruto)
			{
				BasicMaruto myMaruto = (BasicMaruto) actor;

				if (myMaruto.isIsrespawnable() && myMaruto.getlife() < 0)
				{
					if (myMaruto.getTurnsToRespawn() <= 0)
					{

						int xTile = this.generator
								.nextInt(this.mapWidthInTiles);
						int yTile = this.generator
								.nextInt(this.mapHeightInTiles);

						//TODO : check this tile is not water

						basicMaruto.setTilePosX(xTile);
						basicMaruto.setTilePosY(yTile);
						myMaruto.setlife(DefaultValues.ACTOR_LIFE);
						myMaruto.setTurnsToRespawn(DefaultValues.TURNS_TO_RESPAWN);

					}
					else
					{
						myMaruto.setTurnsToRespawn(myMaruto.getTurnsToRespawn() -1);
					}
				}

			}

		}
	}

	/**
	 * 
	 * 
	 * 
	 * - It deletes power-ups after their life span is finished
	 * 
	 * 
	 */
	public void finishTurn()
	{
		Array<Actor> actorList = this.stage.getActors();
		//Checking all actors (no unfinished movement)

		Array<Actor> newActorList = new Array<Actor>();

		for (Actor actor : actorList)
		{

			if (actor instanceof Actor_Powerup)
			{
				Actor_Powerup mypowerup = (Actor_Powerup) actor;

				if (mypowerup.isLifeLimitedByTime() ) 
				{

					mypowerup.setTurnsLife(mypowerup.getTurnsLife() - 1);

					if (mypowerup.getTurnsLife() > 0)
						newActorList.add(actor);
				}

				newActorList.add(actor);
			}
			else
			{
				newActorList.add(actor);	
			}
		}

	}

	public ArrayList<Action> getMovementsFromAction(Action action, int moveMultiplier, boolean isPlayer)
	{
		ArrayList<Action> moveList = new ArrayList<Action>();
		if(action == null) return moveList;
		if (action.gettype() == DefaultValues.ACTIONS.RUN && isPlayer )
		{
			for (int i = 0; i< moveMultiplier; i++)
				moveList.add(new Action(DefaultValues.ACTIONS.WALK));
		}

		else
		{
			moveList.add(action);
		}

		return moveList;
	}

	int obtainItemSpawnX(BasicMaruto myActor) {

		if (myActor.getfacing() == DefaultValues.ABSOLUTE_DIRECTIONS.EAST) {
			return myActor.getTilePosX() + 1;
		} else if (myActor.getfacing() == DefaultValues.ABSOLUTE_DIRECTIONS.WEST) {
			return myActor.getTilePosX() - 1;
		} else {
			return myActor.getTilePosX();
		}

	}

	int obtainItemSpawnY(BasicMaruto myActor) {
		if (myActor.getfacing() == DefaultValues.ABSOLUTE_DIRECTIONS.NORTH) {
			return myActor.getTilePosY() + 1;
		} else if (myActor.getfacing() == DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH) {
			return myActor.getTilePosY() - 1;
		} else {
			return myActor.getTilePosY();
		}
	}

	public Action obtainItemInBox() {

		int nextItem = this.generator.nextInt(11);// FIXME: number of powerups
		// hardcoded

		Action action = null;

		if (nextItem == 0) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.ARRRGGGHHH);
		} else if (nextItem == 1) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.YENDOR);
		} else if (nextItem == 2) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.CHOCO);
		} else if (nextItem == 3) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.GRENADE);
		} else if (nextItem == 4) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.RANDOM);
		} else if (nextItem == 5) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.SHIELD);
		} else if (nextItem == 6) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.INVISIBILITY);
		} else if (nextItem == 7) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.RING);
		} else if (nextItem == 8) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.SONICBOMB);
		} else if (nextItem == 9) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.DIAG_SONICBOMB);
		} else if (nextItem == 10) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.DIZZY);

		}

		return action;
	}

	public void updateInventory() {

		Array<Actor> actorList = this.stage.getActors();
		// Checking all actors (no unfinished movement)



		// Checking powerups
		for (Actor actor : actorList) {
			if (actor instanceof BasicMaruto) {

				BasicMaruto myMaruto = (BasicMaruto) actor;

				ArrayList<String> droppableItems = new ArrayList<String>();


				for (Item_Powerup item : myMaruto.getPowerups()) {
					item.setDuration(item.getDuration() - 1);
					if (item.getDuration() < 0) {
						droppableItems.add(item.getName());
					}
				}

				for (String item : droppableItems) {
					myMaruto.drop(item);
				}
			}
		}


	}

}
