package ludum.mighty.ld36.textTerminal;

import static ludum.mighty.ld36.settings.DefaultValues.LINELENGTH;

import java.util.ArrayList;

import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class TextTerminal implements InputProcessor {

    private static final Color COLOR_INPUT = new Color(0f, 1f, 0f, 1f);
	// public CommandProcessor commandProcessor;

    private BitmapFont bitmapFont;
    private float boxStartPointX;
    private float boxStartPointY;
    private int boxWidth;
    private int boxHeigh;
    private int vMargin;
    private int hMargin;
    private float lineHeigh;
    private Color backgroudColor;

    private ShapeRenderer shapeRenderer;

    private ArrayList<Line> linesList;

    private String prompt;
    private String currentString;

    private char cursor;
    private long lastCursorChange;
    private long millisToChangeCursor;
    private boolean isCursorShowing;
    private boolean enabled;

    public TextTerminal(Vector2 startPoint, int width, int height) {

        this.enabled = true;
        this.boxStartPointX = startPoint.x;
        this.boxStartPointY = startPoint.y;
        this.boxWidth = width;
        this.boxHeigh = height;
        this.hMargin = 5;
        this.vMargin = 5;

        this.backgroudColor = new Color(0f, 0f, 0f, .5f);

        bitmapFont = new BitmapFont(
                Gdx.files.internal("fonts/textTerminalFont.fnt"),
                Gdx.files.internal("fonts/textTerminalFont.png"), false);
        bitmapFont.setColor(COLOR_INPUT);
        this.lineHeigh = bitmapFont.getLineHeight();

        shapeRenderer = new ShapeRenderer();

        linesList = new ArrayList<Line>();

        this.prompt = new String("> ");
        this.currentString = new String(this.prompt);

        this.cursor = '_';
        this.lastCursorChange = TimeUtils.millis();
        this.millisToChangeCursor = 500;
    }


    public void render(SpriteBatch batch) {
        updateCursor();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeType.Filled);

        shapeRenderer.setColor(this.backgroudColor);
        shapeRenderer.rect(this.boxStartPointX, this.boxStartPointY,
                this.boxWidth, this.boxHeigh * -1);
        shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        int firstLineToDisplay = (linesList.size() >= DefaultValues.NUMBEROFLINES) ? (linesList.size() - DefaultValues.NUMBEROFLINES) : 0;
        int i = firstLineToDisplay;
        for (; i < linesList.size(); i++) {
            bitmapFont.setColor(linesList.get(i).getColor());
            bitmapFont.draw(batch, this.linesList.get(i).getText(),
                    this.boxStartPointX
                            + this.hMargin, this.boxStartPointY - this.vMargin
                            - ((i - firstLineToDisplay) * this.lineHeigh),
                    this.boxWidth - (this.hMargin * 2), Align.left, true);
        }
        if(this.enabled) {
            bitmapFont.setColor(COLOR_INPUT);
            if (this.isCursorShowing) {
                bitmapFont.draw(batch, this.currentString + this.cursor,
                        this.boxStartPointX + this.hMargin, this.boxStartPointY
                                - this.vMargin
                                - ((i - firstLineToDisplay) * this.lineHeigh),
                        this.boxWidth - (this.hMargin * 2), Align.left, true);
            } else {
                bitmapFont.draw(batch, this.currentString, this.boxStartPointX
                                + this.hMargin, this.boxStartPointY - this.vMargin
                                - ((i - firstLineToDisplay) * this.lineHeigh),
                        this.boxWidth - (this.hMargin * 2), Align.left, true);
            }
        }
        batch.end();
    }

    private void updateCursor() {
        if (TimeUtils.millis() - this.lastCursorChange > this.millisToChangeCursor) {
            this.isCursorShowing = !this.isCursorShowing;
            this.lastCursorChange = TimeUtils.millis();
        }
    }

    public String getOldestUnprocessedLine() {
        for (Line l : this.linesList) {
            if (l.hasBeenSent())
                continue;
            l.hasBeenSent(true);
			return l.getText().substring(2); // TODO: this substring is not good
        }
        return null;
    }

	public boolean isThereALineNotSent() {
		for (Line l : this.linesList) {
			if (l.hasBeenSent())
				continue;
			return true;
		}
		return false;
	}

	public void addLine(Line line) {
		this.linesList.add(line);
	}

    @Override
    public boolean keyDown(int keycode) {
        if(!this.enabled) return false;
        if (keycode == Keys.ENTER) {
            this.linesList.add(new Line(this.currentString));
			// this.linesList.add(this.commandProcessor.next(this.getOldestUnprocessedLine().substring(2)));
            this.currentString = this.prompt;
        }
        if (keycode == Keys.BACKSPACE) {
            if (this.currentString.length() > 2) {
                this.currentString = this.currentString.substring(0,
                        this.currentString.length() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(!this.enabled) return false;
        if (((character >= 'a' & character <= 'z') |
                (character >= 'A' & character <= 'Z') |
                character == ' ' |
                character == ';') &&
                (this.currentString.length() < LINELENGTH)){
            this.currentString = this.currentString + character;
        }
        return false;
    }


    public void enable(){
        this.enabled = true;
    }

	public void disable() {
		this.enabled = false;
	}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void printLog(String log) {
        this.linesList.add(new Line(this.currentString, true, Line.COLOR_LOG));
    }
}