package ludum.mighty.ld36.world;

import java.util.ArrayList;
import java.util.Random;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.actors.BasicMaruto;
import ludum.mighty.ld36.actors.CommonActor;
import ludum.mighty.ld36.actors.GoodMaruto;
import ludum.mighty.ld36.actors.Powerup;
import ludum.mighty.ld36.settings.DefaultValues;
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
		this.sv = new StretchViewport(640, 480, this.cam);
		this.sv.apply();
		this.cam.position.set(640, 480, 0);

		batch = new SpriteBatch();

		stage = new Stage(sv);

		this.textTerminal = new TextTerminal(new Vector2(0f, 100f), (int) Gdx.graphics.getWidth(),
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

			if (this.timeSinceTurnStarted > DefaultValues.WORLD_SECONDS_FOR_COMMAND_INPUT * 1000) {
				this.currentState = DefaultValues.WORLD_STATE_TURN_INIT;
				System.out.println("moving to state WORLD_STATE_TURN_INIT");
			}
			break;
		case DefaultValues.WORLD_STATE_TURN_INIT:
			this.checkTurnUpdate();
			this.currentState = DefaultValues.WORLD_STATE_MOVEMENT_INIT;
			break;
		case DefaultValues.WORLD_STATE_TURN_END:
			//TODO: Doing Nothing?
			//Update turn-based items, respawn, etc
			this.finishTurn();
			break;
		case DefaultValues.WORLD_STATE_MOVEMENT_INIT:

			if (this.isMovementStepFinished())
			{
				if (this.nextMovementUpdate())
				{
					this.currentState = DefaultValues.WORLD_STATE_MOVEMENT_END;
				}
				else
				{
					this.currentState = DefaultValues.WORLD_STATE_TURN_END;
				}
			}
			break;
		case DefaultValues.WORLD_STATE_MOVEMENT_END:

			//Wait till movements are finished
			if (this.isMovementStepFinished())
			{
				this.finishMovement();
				this.checkRespawn();

				this.currentState = DefaultValues.WORLD_STATE_MOVEMENT_INIT;

			}

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
		basicMaruto.checkAction();

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
		basicMaruto = new GoodMaruto(this.textTerminal.commandProcessor);

		// set the xy for the tiles and stage position
		//TODO: set this position randomly
		basicMaruto.setTilePosX(20);
		basicMaruto.setTilePosY(20);
		this.stage.addActor(basicMaruto);

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

			if (!myActor.isMovementFinished())
			{
				allMovementsFinished = false;
			}
		}

		return allMovementsFinished;
	}

	public boolean nextMovementUpdate()
	{

		boolean actionsPending = false;

		Array<Actor> actorList = this.stage.getActors();
		//Checking all actors (no unfinished movement)

		//Checking powerups
		for (Actor actor : actorList)
		{
			if (actor instanceof Powerup)
			{
				Powerup mypowerup = (Powerup) actor;

				if (mypowerup.getMovementList().size() > 0)
				{

					Action movement = mypowerup.getMovementList().remove(0);

					mypowerup.setMovementFinished(false); //TODO:shold be set to true by the Actor after the render is finished
					mypowerup.doMovement(movement);

					//Check action outcome
					for (Actor nextActor : actorList)
					{
						if (nextActor != actor)
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

		//Checking Players
		for (Actor actor : actorList)
		{
			//TODO
			if (actor instanceof BasicMaruto)
			{
				BasicMaruto myMaruto = (BasicMaruto) actor;

				if (myMaruto.getMovementList().size() > 0)
				{
					Action movement = myMaruto.getMovementList().remove(0);

					myMaruto.setMovementFinished(false); //TODO:shold be set to true by the Actor after the render is finished
					myMaruto.doMovement(movement);

					//Check action outcome
					for (Actor nextActor : actorList)
					{
						if (nextActor != actor)
						{
							if (nextActor instanceof Powerup)
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
								//Maruto Against Maruto (a headbump)

								myMaruto.setlife(myMaruto.getlife() - DefaultValues.MARUTO_HEADBUMP_DAMAGE);

								if (myMaruto.getlife() <= 0)
								{
									myMaruto.getMovementList().clear();
									myMaruto.getMovementList().add(new Action(DefaultValues.ACTIONS.DEATH));
								}
								else
								{
									myMaruto.getMovementList().clear();
									myMaruto.getMovementList().add(new Action(DefaultValues.ACTIONS.SHIFT_HIT));
								}

							}
						}
					}
				}

			}

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
			if (actor instanceof Powerup)
			{
				Powerup mypowerup = (Powerup) actor;

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

			if (actor instanceof Powerup)
			{
				Powerup mypowerup = (Powerup) actor;

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



}
