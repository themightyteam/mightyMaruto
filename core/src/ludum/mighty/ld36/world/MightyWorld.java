package ludum.mighty.ld36.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.actors.Actor_Powerup;
import ludum.mighty.ld36.actors.BasicMaruto;
import ludum.mighty.ld36.actors.CommonActor;
import ludum.mighty.ld36.actors.EvilMaruto;
import ludum.mighty.ld36.actors.GoodMaruto;
import ludum.mighty.ld36.actors.ItemBlackBox;
import ludum.mighty.ld36.actors.Item_ARRRGGGHHH;
import ludum.mighty.ld36.actors.Item_Boomerang;
import ludum.mighty.ld36.actors.Item_Choco;
import ludum.mighty.ld36.actors.Item_Powerup;
import ludum.mighty.ld36.actors.Item_Punch;
import ludum.mighty.ld36.actors.Item_SonicBoom;
import ludum.mighty.ld36.ai.AI;
import ludum.mighty.ld36.powerUpsViewer.PowerUpsViewer;
import ludum.mighty.ld36.score.ScoreLabel;
import ludum.mighty.ld36.score.TurnLabel;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.settings.DefaultValues.POWERUPS;
import ludum.mighty.ld36.textTerminal.CommandProcessor;
import ludum.mighty.ld36.textTerminal.TextTerminal;
import ludum.mighty.ld36.timeLeftLabel.TimeLeftLabel;
import ludum.mighty.ld36.utils.ScoreUtils;

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
	private ScoreLabel scoreLabel;
	private TurnLabel turnsLabel;
	private PowerUpsViewer powerUpsViewer;

	private CommandProcessor parser;

	boolean actionsPending = false;

	Random generator = new Random();

	int numberOfTurns;

	private AI ai;

	// // WORLD API
	// Loads stuff like the map and initializes things
	public void init(TiledMap map) {

		this.ai = new AI(this);
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

		this.scoreLabel = new ScoreLabel(new Vector2(200f, h - 10f), 120, 20);
		this.scoreLabel.setYourScore(0);

		this.turnsLabel = new TurnLabel(new Vector2(400f, h - 10f), 120, 20,
				DefaultValues.MAXIMUM_NUMBER_TURNS);
		this.turnsLabel.setTurns(this.numberOfTurns);

		this.timeLeftLabel = new TimeLeftLabel(new Vector2(10f, h - 10f), 160,
				20);
		this.timeLeftLabel
				.setTimeLeft(((float) DefaultValues.WORLD_SECONDS_FOR_COMMAND_INPUT)
						- (float) this.timeSinceTurnStarted / 1000);

		this.parser = new CommandProcessor();

		this.powerUpsViewer = new PowerUpsViewer(new Vector2(550f, 10f));

		// Init the number of turns
		this.numberOfTurns = 0;

		// TODO: define user input here

		this.currentState = DefaultValues.WORLD_STATE_ENTERING_COMMAND;
		// initPlayersDebug();
		this.initPlayers();
		updatePowerUps();
		// initBlackBoxesDebug();
		this.initBlackBoxes();


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
			this.ai.updateActors();
			this.checkTurnUpdate();
			this.currentState = DefaultValues.WORLD_STATE_MOVEMENT_INIT;
			break;
		case DefaultValues.WORLD_STATE_TURN_END:
			System.out.println("WORLD_STATE_TURN_END");
			//TODO: Doing Nothing?
			//Update turn-based items, respawn, etc
			
			this.updateInventory();
			this.finishTurn();

			this.textTerminal.enable();
			this.timeWhenTurnStarted = TimeUtils.millis();

			// Increment the number of turns played
			this.numberOfTurns += 1;


			// Update labels
			// Update score in label
			this.scoreLabel.setYourScore(this.basicMaruto.getScore());

			// Update turns label
			this.turnsLabel.setTurns(this.numberOfTurns);

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
			/**
			 * // Checking Players for (Actor actor : this.stage.getActors()) {
			 * if (actor instanceof BasicMaruto) { BasicMaruto myMaruto =
			 * (BasicMaruto) actor;
			 * 
			 * System.out.println("X: " + Float.toString(actor.getX()) +
			 * "  TILED X: " + Integer.toString(((BasicMaruto) actor)
			 * .getTilePosX()) + "   Y: " + Float.toString(actor.getY()) +
			 * "  TILED Y: " + Integer.toString(((BasicMaruto) actor)
			 * .getTilePosY()) ); } } System.out
			 * .println("1 BASIC MARUTO SCORE " + basicMaruto.getScore() +
			 * " NAME " + basicMaruto.getName());
			 */
			//Wait till movements are finished
			// if (this.isMovementStepFinished())
			// {



			this.updatePowerupsPlayer();


			this.deleteOutOfBordersActors();


			this.deletePowerups();


			this.checkRespawn();
		

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

	public Stage getStage() {
		return stage;
	}

	private void updatePowerupsPlayer() {

		ArrayList<POWERUPS> powerList = new ArrayList<POWERUPS>();

		for (int i = 0; i < this.basicMaruto.getPowerups().size(); i++) {

			powerList.add(this.basicMaruto.getPowerups().get(i).getType());
		}

		for (int i = powerList.size(); i < 4; i++)
		{
			powerList.add(null);
		}

		this.powerUpsViewer.setList(powerList.get(0), powerList.get(1),
				powerList.get(2), powerList.get(3));
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
		this.powerUpsViewer.render(batch);
		this.scoreLabel.render(batch);
		this.turnsLabel.render(batch);

	}

	// // END OF WORLD API

	// Creates all players

	private void initPlayers() {
		// Add playable player
		basicMaruto = new GoodMaruto();

		// set the xy for the tiles and stage position
		// TODO: set this position randomly
		doRespawn(basicMaruto);
		this.stage.addActor(basicMaruto);

		for (int i = 0; i < DefaultValues.NUMBER_EVIL_MARUTOS; i++) {
			EvilMaruto eM = new EvilMaruto(i);
			doRespawn(eM);
			this.stage.addActor(eM);
		}

	}

	private void initPlayersDebug() {
		// Add playable player
		basicMaruto = new GoodMaruto();

		// set the xy for the tiles and stage position
		//TODO: set this position randomly
		basicMaruto.setInitialTilePosX(24);
		basicMaruto.setInitialTilePosY(24);
		this.stage.addActor(basicMaruto);

		EvilMaruto eM = new EvilMaruto();
		eM.setInitialTilePosX(27);
		eM.setInitialTilePosY(24);
		this.stage.addActor(eM);

		eM = new EvilMaruto();
		eM.setInitialTilePosX(25);
		eM.setInitialTilePosY(27);
		this.stage.addActor(eM);

		eM = new EvilMaruto();
		eM.setInitialTilePosX(27);
		eM.setInitialTilePosY(27);
		this.stage.addActor(eM);

		// TODO: Add rest of players
	}

	private void initBlackBoxes() 
	{
		for (int i = 0; i < DefaultValues.NUMBER_BLACKBOXES; i++)
		{
			ItemBlackBox ibb = new ItemBlackBox();
			this.doRespawn(ibb);
			this.stage.addActor(ibb);
		}
			
	}

	private void initBlackBoxesDebug() {
		ItemBlackBox ibb = new ItemBlackBox();
		ibb.setInitialTilePosX(25);
		ibb.setInitialTilePosY(25);
		this.stage.addActor(ibb);

		ibb = new ItemBlackBox();
		ibb.setInitialTilePosX(28);
		ibb.setInitialTilePosY(28);
		this.stage.addActor(ibb);

		ibb = new ItemBlackBox();
		ibb.setInitialTilePosX(10);
		ibb.setInitialTilePosY(10);
		this.stage.addActor(ibb);

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
		Array<Actor> newActorList = new Array<Actor>();

		//Checking powerups
		for (int i = 0; i < actorList.size; i++)
		{
			Actor actor = actorList.get(i);

			if (actor instanceof Actor_Powerup)
			{
				Actor_Powerup mypowerup = (Actor_Powerup) actor;



				if (!mypowerup.isHarmfull())
					continue;


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

							// Jump if the player was already dead
							if (otherActor.getlife() <= 0)
								continue;

							// Check if the player and the powerup are in the
							// same tile
							if ((otherActor.getTilePosX() == mypowerup.getTilePosX()) &&
									(otherActor.getTilePosY() == mypowerup.getTilePosY()))
							{

								//Update life

								if (otherActor.isCanBeHit())
								{
									//Check if the user is shielded 
									if (nextActor instanceof BasicMaruto)
									{
										BasicMaruto otherMaruto = (BasicMaruto) nextActor;
										if (!otherMaruto.isShielded())
											otherActor.setlife(otherActor
													.getlife()
													- mypowerup.getpunch());
									}
									else
									{

										otherActor.setlife(otherActor.getlife() - mypowerup.getpunch());
									}
								}

								//life of my powerup is also diminished by 1 (losing force after collision)
								mypowerup.setlife(mypowerup.getlife() - 1);



								// If the other actor is dead remove its actions
								// and add a DEATH
								if (otherActor.getlife() <= 0 )
								{

									// Update scores
									if (otherActor instanceof BasicMaruto) {

										BasicMaruto otherMaruto = (BasicMaruto) otherActor;

										mypowerup
												.getParentActor()
												.setScore(
														mypowerup
																.getParentActor()
																.getScore()
																+ DefaultValues.POINTS_KILL);

										otherMaruto
												.setScore(otherMaruto
														.getScore()
														+ DefaultValues.POINTS_ITEM_DEATH);

									}


									otherActor.getMovementList().clear();
									otherActor.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
								}
								else
								{

									// If the other actor was hit, change the
									// actions

									if (otherActor.isCanBeHit())
									{
										//Add a hit or a shifted hit
										otherActor.getMovementList().clear();

										float randomProb = this.generator.nextFloat();

										if (nextActor instanceof BasicMaruto)
										{
											BasicMaruto otherMaruto = (BasicMaruto) nextActor;

											// Check if the other player was
											// shielded
											if (!otherMaruto.isShielded()) {
												if (randomProb < mypowerup
														.getShiftProbability())
 {
													otherActor
															.setFacing(this
																	.obtainOppositeFacing(mypowerup
																			.getFacing()));

													otherActor
															.getMovementList()
															.add(new Action(
																	DefaultValues.ACTIONS.SHIFT_HIT));
												}
												else
													otherActor
															.getMovementList()
															.add(new Action(
																	DefaultValues.ACTIONS.HIT));
											}
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

				}
			}
		}

		// Checking Marutos
		for (int i = 0; i < actorList.size; i++)
		{
			Actor actor = actorList.get(i);
			//TODO
			if (actor instanceof BasicMaruto)
			{
				BasicMaruto myMaruto = (BasicMaruto) actor;

				// If the player was already death do nothing

				/*
				 * if (myMaruto.getlife() <= 0) { if
				 * (myMaruto.getMovementList().size() > 0) {
				 * myMaruto.getMovementList().clear(); } continue; }
				 */

				if (myMaruto.getMovementList().size() > 0)
				{
					actionsPending = true;

					Action movement = myMaruto.getMovementList().remove(0);


					// Check changes
					if (movement.gettype() == DefaultValues.ACTIONS.SHOOT) {

						CommonActor newActor;

						if (movement.getpowerup() != null) {

						switch (movement.getpowerup()) {

						case ARRRGGGHHH:

							if (myMaruto
									.hasPowerUp(DefaultValues.POWERUPS.ARRRGGGHHH
											.toString())) {

								newActor = new Item_ARRRGGGHHH(myMaruto);

								newActor.setFacing(myMaruto.getfacing());

								// Set x-y position of item (initial position)
								newActor.setInitialTilePosX(this
										.obtainItemSpawnX(myMaruto));
								newActor.setInitialTilePosY(this
										.obtainItemSpawnY(myMaruto));
								newActorList.add(newActor);
							} else {
								myMaruto.getMovementList().clear();
								myMaruto.getMovementList()
								.add(new Action(
										DefaultValues.ACTIONS.CONFUSION));
							}

							break;
						case CHOCO:
							if (myMaruto
									.hasPowerUp(DefaultValues.POWERUPS.CHOCO
											.toString())) {

								newActor = new Item_Choco(myMaruto);

							newActor.setFacing(myMaruto.getfacing());
							// Set x-y position of item (initial position)
							newActor.setInitialTilePosX(this
									.obtainItemSpawnX(myMaruto));
							newActor.setInitialTilePosY(this
									.obtainItemSpawnY(myMaruto));
							newActorList.add(newActor);

							} else {
								myMaruto.getMovementList().clear();
								myMaruto.getMovementList()
										.add(new Action(
												DefaultValues.ACTIONS.CONFUSION));
							}

							break;
						case SONICBOMB:
							if (myMaruto
									.hasPowerUp(DefaultValues.POWERUPS.SONICBOMB
											.toString())) {

								newActor = new Item_SonicBoom(myMaruto);
							newActor.setFacing(myMaruto.getfacing());

							// Set x-y position of item (initial position)
							newActor.setInitialTilePosX(this
									.obtainItemSpawnX(myMaruto));
							newActor.setInitialTilePosY(this
									.obtainItemSpawnY(myMaruto));
							newActorList.add(newActor);

							} else {
								myMaruto.getMovementList().clear();
								myMaruto.getMovementList()
										.add(new Action(
												DefaultValues.ACTIONS.CONFUSION));
							}

							break;
						case GRENADE:

							if (myMaruto
									.hasPowerUp(DefaultValues.POWERUPS.GRENADE
											.toString())) {
								newActor = new Item_SonicBoom(myMaruto);

							newActor.setFacing(myMaruto.getfacing());

							// Set x-y position of item (initial position)
							newActor.setInitialTilePosX(this
									.obtainItemSpawnX(myMaruto));
							newActor.setInitialTilePosY(this
									.obtainItemSpawnY(myMaruto));
							newActorList.add(newActor);

							} else {
								myMaruto.getMovementList().clear();
								myMaruto.getMovementList()
										.add(new Action(
												DefaultValues.ACTIONS.CONFUSION));
							}

							break;
						case PUNCH:
							newActor = new Item_Punch(myMaruto);

							newActor.setFacing(myMaruto.getfacing());

							// Set x-y position of item (initial position)
							newActor.setInitialTilePosX(this
									.obtainItemSpawnX(myMaruto));
							newActor.setInitialTilePosY(this
									.obtainItemSpawnY(myMaruto));
							newActorList.add(newActor);

							System.out.println("Creating punch "
									+ newActor.getTilePosX() + " "
									+ newActor.getTilePosY());

							break;
						case DIAG_SONICBOMB:
							break;
						case RANDOM:
							if (myMaruto
									.hasPowerUp(DefaultValues.POWERUPS.RANDOM
											.toString())) {

								newActor = new Item_Boomerang(myMaruto);

							newActor.setInitialTilePosX(this
									.obtainItemSpawnX(myMaruto));
							newActor.setInitialTilePosY(this
									.obtainItemSpawnY(myMaruto));

							int nextFacing = this.generator.nextInt(4);

							if (nextFacing == 0)
								newActor.setFacing(DefaultValues.ABSOLUTE_DIRECTIONS.EAST);
							else if (nextFacing == 1)
								newActor.setFacing(DefaultValues.ABSOLUTE_DIRECTIONS.WEST);
							else if (nextFacing == 2)
								newActor.setFacing(DefaultValues.ABSOLUTE_DIRECTIONS.NORTH);
							else
								newActor.setFacing(DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH);

							newActorList.add(newActor);

							} else {
								myMaruto.getMovementList().clear();
								myMaruto.getMovementList()
										.add(new Action(
												DefaultValues.ACTIONS.CONFUSION));
							}

							break;

						default:
							// No item: confuse
							myMaruto.getMovementList().clear();
							myMaruto.getMovementList()
									.add(new Action(
											DefaultValues.ACTIONS.CONFUSION));

							break;
						}

						}
					} else if (movement.gettype() == DefaultValues.ACTIONS.DROP) {

						if (movement.getpowerup() != null) {

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

						}

					} else if (movement.gettype() == DefaultValues.ACTIONS.PICK) {

						if (movement.getpowerup() != null) {

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
						case RANDOM:
							myMaruto.pickup(new Item_Powerup(
									DefaultValues.POWERUPS.RANDOM,
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

							for (int j = 0; j < actorList.size; j++)
							{
								Actor possibleItem = actorList.get(j);	

								// Handling the special case of ItemBlackBox
								if (possibleItem instanceof ItemBlackBox) {

									ItemBlackBox itemActor = (ItemBlackBox) possibleItem;
									if ((itemActor.getTilePosX() == myMaruto.getTilePosX()) &&
											(itemActor.getTilePosY() == myMaruto.getTilePosY()))
									{
										if ((itemActor.getTilePosX() == myMaruto
												.getTilePosX())
												&& (itemActor.getTilePosY() == myMaruto
												.getTilePosY())) {

											// Pick the object

											Action nextAction = this
													.obtainItemInBox();
											// Grab the item in the next
											// movement
											myMaruto.getMovementList().add(0,
													nextAction);

											// Respawn the object
											int xTile = this.generator
													.nextInt(this.mapWidthInTiles) / 2; // FIXME:
											// nhapa:
											// dentro
											// del
											// tatami
											int yTile = this.generator
													.nextInt(this.mapHeightInTiles) / 2; // FIXME:
											// nhapa
											// dentro
											// del
											// tatami

											xTile += this.mapWidthInTiles / 4;

											yTile += this.generator
													.nextInt(this.mapWidthInTiles) / 4;

											itemActor.setX(xTile);
											itemActor.setY(yTile);

										}
									}
								}

							}

							break;

						default:
							break;

							}
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

									// Handling the special case of ItemBlackBox
									if (otherActor instanceof ItemBlackBox) {

										continue;
									}

									// Normal items

									//Update life
									otherActor.setlife(otherActor.getlife() - 1);

									//life of my powerup is also diminished by 1 (losing force after collision)
									otherActor.setlife(otherActor.getlife() - 1);
									if (myMaruto.isCanBeHit())
 {
										if (!myMaruto.isShielded())
											myMaruto.setlife(myMaruto.getlife()
													- otherActor.getpunch());
									}

									//Check powerup life
									if (otherActor.getlife() < 0)
									{
										otherActor.getMovementList().clear();
										otherActor.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
									}

									//Check my maruto life
									if (myMaruto.getlife() <= 0 )
									{

										Actor_Powerup mypowerup = (Actor_Powerup) otherActor;

										mypowerup
												.getParentActor()
												.setScore(
														mypowerup
																.getParentActor()
																.getScore()
																+ DefaultValues.POINTS_KILL);

										// Update scores
										myMaruto.setScore(myMaruto.getScore()
												+ DefaultValues.POINTS_ITEM_DEATH);

										myMaruto.getMovementList().clear();
										myMaruto.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
									}
									else
									{
										if (!myMaruto.isShielded()
												&& myMaruto.isCanBeHit()) {
											float randomProb = this.generator
													.nextFloat();
											myMaruto.getMovementList().clear();
											if (randomProb < myMaruto
													.getShiftProbability())
 {
												myMaruto.setFacing(this
														.obtainOppositeFacing(otherActor
																.getFacing()));

												myMaruto.getMovementList()
														.add(new Action(
																DefaultValues.ACTIONS.SHIFT_HIT));
											}
											else
												myMaruto.getMovementList()
														.add(new Action(
																DefaultValues.ACTIONS.HIT));
										}
									}

								}

							} 

							// Special Case: maruto headbump
							// The headbump also affects if maruto is shielded
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

										// Update score
										myMaruto.setScore(myMaruto.getScore()
												+ DefaultValues.POINTS_ITEM_DEATH);

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
								else {

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

		for (Actor actor : this.stage.getActors()) {
			CommonActor myActor = (CommonActor) actor;

			if (myActor.getMovementList().size() > 0) {
				this.actionsPending = true;
			}
		}

		return actionsPending;
	}

	public void deleteOutOfBordersActors() {
		Array<Actor> actorList = this.stage.getActors();
		// Checking all actors (no unfinished movement)

		Array<Actor> newActorList = new Array<Actor>();

		// Delete actors
		for (Actor actor : actorList) {
			if (actor instanceof Actor_Powerup) {
				Actor_Powerup mypowerup = (Actor_Powerup) actor;

				if ((mypowerup.getTilePosX() > this.mapWidthInTiles / 4 - 2)
						&& (mypowerup.getTilePosX() <= this.mapWidthInTiles
								- this.mapWidthInTiles / 4)) {

					if ((mypowerup.getTilePosY() >= this.mapHeightInTiles / 4 - 1)
							&& (mypowerup.getTilePosY() <= this.mapHeightInTiles
									- this.mapHeightInTiles / 4)) {

						newActorList.add(actor);
					}
				}

			} else {
				// Always add actor
				newActorList.add(actor);

				BasicMaruto myMaruto = (BasicMaruto) actor;

				if ((myMaruto.getTilePosX() < this.mapWidthInTiles / 4 - 1)
						|| (myMaruto.getTilePosX() > this.mapWidthInTiles
								- this.mapWidthInTiles / 4)) {

					if (myMaruto.getlife() > 0) {
						myMaruto.getMovementList().clear();
						myMaruto.getMovementList().add(
								new Action(DefaultValues.ACTIONS.DEATH));
						myMaruto.setlife(Integer.MIN_VALUE);
					}

				}
				if ((myMaruto.getTilePosY() < this.mapHeightInTiles / 4 - 1)
						|| (myMaruto.getTilePosY() > this.mapHeightInTiles
								- this.mapHeightInTiles / 4 - 1)) {

					if (myMaruto.getlife() > 0) {
						myMaruto.getMovementList().clear();
						myMaruto.getMovementList().add(
								new Action(DefaultValues.ACTIONS.DEATH));

						myMaruto.setlife(Integer.MIN_VALUE);

						// Update score
						myMaruto.setScore(myMaruto.getScore()
								+ DefaultValues.POINTS_OUT_OF_BOUNDS_DEATH);


					}

				}

			}
		}

		this.stage.getActors().clear();

		for (Actor actor : newActorList) {
			this.stage.addActor(actor);
		}

	}

	/**
	 * 
	 * 
	 * - It deletes power-ups after their collide with something and they do not have life remaining
	 * (typically a power-up will have 1 value of live, least the arrrggghhh monster which is invincible )
	 * 
	 */
	public void deletePowerups()
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

				if (mypowerup.getlife() > 0 && mypowerup.getTurnsLife() > 0)
				{
					// Delete one turn of powerup
					mypowerup.setTurnsLife(mypowerup.getTurnsLife() - 1);

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

						this.doRespawn(myMaruto);
						myMaruto.setlife(DefaultValues.ACTOR_LIFE);
						myMaruto.setTurnsToRespawn(DefaultValues.TURNS_TO_RESPAWN);
						myMaruto.getMovementList().clear();



					}
					else
					{
						myMaruto.setTurnsToRespawn(myMaruto.getTurnsToRespawn() -1);
					}
				}

			}

		}
	}

	public void doRespawn(CommonActor actor) {
		int xTile = this.generator.nextInt(this.mapWidthInTiles) / 2; // FIXME:
		// nhapa:
		// dentro
		// del
		// tatami
		int yTile = this.generator.nextInt(this.mapHeightInTiles) / 2; // FIXME:
		// nhapa
		// dentro
		// del
		// tatami

		xTile += this.mapWidthInTiles / 4;

		yTile += this.mapHeightInTiles / 4;

		// TODO : check this tile is not water

		actor.setInitialTilePosX(xTile);
		actor.setInitialTilePosY(yTile);

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

		int nextItem = this.generator.nextInt(12);// FIXME: number of powerups
		// hardcoded

		Action action = null;

		if (nextItem == 0) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.ARRRGGGHHH);
		} else if (nextItem == 1) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.YENDOR);
		}
		/*
		 * else if (nextItem == 2) { action = new
		 * Action(DefaultValues.ACTIONS.PICK, DefaultValues.POWERUPS.CHOCO); }
		 */
		/*
		 * else if (nextItem == 3) { action = new
		 * Action(DefaultValues.ACTIONS.PICK, DefaultValues.POWERUPS.GRENADE); }
		 */
		else if (nextItem == 4) {
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

		}
		/*
		 * else if (nextItem == 9) { action = new
		 * Action(DefaultValues.ACTIONS.PICK,
		 * DefaultValues.POWERUPS.DIAG_SONICBOMB); }
		 */
		else if (nextItem == 10) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.DIZZY);

		} else if (nextItem == 11) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.SNEAKERS);

		}

		if (action == null) {
			action = new Action(DefaultValues.ACTIONS.PICK,
					DefaultValues.POWERUPS.YENDOR);
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

	DefaultValues.ABSOLUTE_DIRECTIONS obtainOppositeFacing(
			DefaultValues.ABSOLUTE_DIRECTIONS direction) {
		DefaultValues.ABSOLUTE_DIRECTIONS newDirection;

		switch (direction) {
		case NORTH:
			newDirection = DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH;
			break;
		case SOUTH:
			newDirection = DefaultValues.ABSOLUTE_DIRECTIONS.NORTH;
			break;
		case EAST:
			newDirection = DefaultValues.ABSOLUTE_DIRECTIONS.WEST;
			break;
		case WEST:
			newDirection = DefaultValues.ABSOLUTE_DIRECTIONS.EAST;
		default:
			newDirection = DefaultValues.ABSOLUTE_DIRECTIONS.SOUTH;
		}

		return newDirection;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	public ArrayList<ScoreUtils> obtainMarutoScores() {
		
		ArrayList<ScoreUtils> nextScores = new ArrayList<ScoreUtils>();
		
		for (Actor actor : this.stage.getActors()) {
			if (actor instanceof BasicMaruto) {

				BasicMaruto myMaruto = (BasicMaruto) actor;

				nextScores.add(new ScoreUtils(myMaruto.getName(), myMaruto
						.getScore()));
			}
		}

		Collections.sort(nextScores);

		return nextScores;
	}

}
