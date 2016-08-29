package ludum.mighty.ld36.score;

import java.util.ArrayList;

import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.utils.ScoreUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class TopScorersLabel {

	
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




	public TopScorersLabel(Vector2 startPoint, int width, int height, ArrayList<ScoreUtils> scoreList) {
		this.boxStartPointX = startPoint.x;
		this.boxStartPointY = startPoint.y;
		this.boxWidth = width;
		this.boxHeigh = height;
		this.hMargin = 5;
		this.vMargin = 5;

		this.constantString = new String();

		for (int i = 0; i < scoreList.size(); i++) {

			if (i == DefaultValues.MAXIMUM_LEADERS_SCORESCREEN)
				break;


			this.constantString = this.constantString + Integer.toString(i + 1)
					+ "        " + scoreList.get(i).getName() + "        "
					+ Integer.toString(scoreList.get(i).getScore()) + "\n";
		}

		this.backgroudColor = new Color(0f, 0f, 0f, .5f);

		this.bitmapFont = new BitmapFont(
				Gdx.files.internal("fonts/textTerminalFont.fnt"),
				Gdx.files.internal("fonts/textTerminalFont.png"), false);

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
		bitmapFont.draw(batch,
 this.constantString,
				this.boxStartPointX + this.hMargin, this.boxStartPointY
						- this.vMargin, this.boxWidth, Align.left, true);
		batch.end();
	}

	
}
