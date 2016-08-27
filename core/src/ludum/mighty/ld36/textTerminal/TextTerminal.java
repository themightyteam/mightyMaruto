package ludum.mighty.ld36.textTerminal;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class TextTerminal implements InputProcessor {

    private CommandProcessor commandProcessor;

    // a introduced string line with some status codes
    private class Line {
        public String text;
        public boolean hasBeenSent; // if the line has been given to the game
        // (to process or whatever)

        Line(String text) {
            this.text = text;
            this.hasBeenSent = false;
        }
    }

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

    // TODO: Enable/disable text input

    public TextTerminal(Vector2 startPoint, int width, int height) {

        this.boxStartPointX = startPoint.x;
        this.boxStartPointY = startPoint.y;
        this.boxWidth = width;
        this.boxHeigh = height;
        this.hMargin = 5;
        this.vMargin = 5;
        this.commandProcessor = new CommandProcessor();

        this.backgroudColor = new Color(0f, 0f, 0f, .5f);

        bitmapFont = new BitmapFont(
                Gdx.files.internal("fonts/textTerminalFont.fnt"),
                Gdx.files.internal("fonts/textTerminalFont.png"), false);
        bitmapFont.setColor(0f, 1f, 0f, 1f);
        this.lineHeigh = bitmapFont.getLineHeight();

        shapeRenderer = new ShapeRenderer();

        linesList = new ArrayList<Line>();
        linesList.add(new Line("> line 1"));
        linesList.add(new Line("> line 2"));
        linesList.add(new Line("> line 3"));
        linesList.add(new Line("> line 4"));
        linesList.add(new Line("> line 5"));
        linesList.add(new Line("> line 6"));
        linesList.add(new Line("> line 7"));
        linesList.add(new Line("> line 8"));
        linesList.add(new Line("> line 9"));

        this.prompt = new String("> ");
        this.currentString = new String(this.prompt);

        this.cursor = '_';
        this.lastCursorChange = TimeUtils.millis();
        this.millisToChangeCursor = 1000;
    }


    public void render(SpriteBatch batch) {

        updateCursor();

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(this.backgroudColor);
        shapeRenderer.rect(this.boxStartPointX, this.boxStartPointY,
                this.boxWidth, this.boxHeigh * -1);
        shapeRenderer.end();

        batch.begin();
        int firstLineToDisplay = linesList.size() - 4;
        int i = firstLineToDisplay;
        for (; i < linesList.size(); i++) {
            bitmapFont.draw(batch, this.linesList.get(i).text,
                    this.boxStartPointX
                            + this.hMargin, this.boxStartPointY - this.vMargin
                            - ((i - firstLineToDisplay) * this.lineHeigh),
                    this.boxWidth - (this.hMargin * 2), Align.left, true);
        }
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
            if (l.hasBeenSent)
                continue;
            l.hasBeenSent = true;
            return l.text;
        }
        return null;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.ENTER) {
            this.linesList.add(new Line(this.currentString));
            this.currentString = this.prompt;
            this.commandProcessor.next(this.getOldestUnprocessedLine());
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
        if ((character >= 'a' & character <= 'z')
                | (character >= 'A' & character <= 'Z') | character == ' ') {
            this.currentString = this.currentString + character;
        }
        return false;
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

}