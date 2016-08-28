package ludum.mighty.ld36.timeLeftLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class TimeLeftLabel {

	private BitmapFont bitmapFont;
	private float boxStartPointX;
	private float boxStartPointY;
	private int boxWidth;
	private int boxHeigh;
	private int vMargin;
	private int hMargin;
	private Color backgroudColor;

	private ShapeRenderer shapeRenderer;

	private float timeLeft;
	private String currentString;
	private String constantString;

	public TimeLeftLabel(Vector2 startPoint, int width, int height) {
		this.boxStartPointX = startPoint.x;
		this.boxStartPointY = startPoint.y;
		this.boxWidth = width;
		this.boxHeigh = height;
		this.hMargin = 5;
		this.vMargin = 5;

		this.constantString = new String("Time left: ");

		this.backgroudColor = new Color(0f, 0f, 0f, .5f);

		this.bitmapFont = new BitmapFont(
				Gdx.files.internal("fonts/textTerminalFont.fnt"),
				Gdx.files.internal("fonts/textTerminalFont.png"), false);

		setTimeLeft(3.0f);

		this.shapeRenderer = new ShapeRenderer();
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
		bitmapFont.draw(batch, this.currentString,
				this.boxStartPointX + this.hMargin, this.boxStartPointY
						- this.vMargin, this.boxWidth, Align.left, true);
		batch.end();
	}

	String format(float n) {
		String ret = String.valueOf((int) n);
		int left = (int) (n * 100 - ((int)n * 100));

		if (left <= 0) {
			ret += ".00";
		} else if(left < 10) {
			ret = ret + ".0" + String.valueOf(left);
		} else {
			ret = ret + "." + String.valueOf(left);
		}
		
		return ret;
	}

	public void setTimeLeft(float time) {
		this.timeLeft = time;
		this.currentString = this.constantString + format(this.timeLeft);
		if (this.timeLeft > 2) {
			this.bitmapFont.setColor(0f, 1f, 0f, 1f);
		} else if (this.timeLeft > 1) {
			this.bitmapFont.setColor(1f, 0.5f, 0f, 1f);
		} else if (this.timeLeft > 0) {
			this.bitmapFont.setColor(1f, 0.0f, 0f, 1f);
		}
	}

	public float getTimeLeft() {
		return this.timeLeft;
	}
}
