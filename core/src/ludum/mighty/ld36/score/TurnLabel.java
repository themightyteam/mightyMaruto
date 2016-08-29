package ludum.mighty.ld36.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class TurnLabel {

	private BitmapFont bitmapFont;
	private float boxStartPointX;
	private float boxStartPointY;
	private int boxWidth;
	private int boxHeigh;
	private int vMargin;
	private int hMargin;
	private Color backgroudColor;

	private ShapeRenderer shapeRenderer;

	String constantString;

	int turns;
	int maximumTurns;

	public TurnLabel(Vector2 startPoint, int width, int height, int maximumTurns) {
		this.boxStartPointX = startPoint.x;
		this.boxStartPointY = startPoint.y;
		this.boxWidth = width;
		this.boxHeigh = height;
		this.hMargin = 5;
		this.vMargin = 5;

		this.backgroudColor = new Color(0f, 0f, 0f, .5f);

		this.bitmapFont = new BitmapFont(
				Gdx.files.internal("fonts/textTerminalFont.fnt"),
				Gdx.files.internal("fonts/textTerminalFont.png"), false);

		this.shapeRenderer = new ShapeRenderer();

		this.maximumTurns = maximumTurns;
	}

	public void render(SpriteBatch batch) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);

		shapeRenderer.setColor(this.backgroudColor);
		shapeRenderer.rect(this.boxStartPointX, this.boxStartPointY,
				this.boxWidth, this.boxHeigh * -1);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		batch.begin();
		bitmapFont.draw(batch,
 "Turn " + Integer.toString(this.turns) + " of "
				+ this.maximumTurns,
				this.boxStartPointX + this.hMargin, this.boxStartPointY
						- this.vMargin, this.boxWidth, Align.left, true);
		batch.end();
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}


}
