package ludum.mighty.ld36.textTerminal;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by dchaves on 28/08/16.
 */
// a introduced string line with some status codes
public class Line {
    public Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public boolean hasBeenSent() {
        return hasBeenSent;
    }

    private Color color;
    private String text;

    public void hasBeenSent(boolean hasBeenSent) {
        this.hasBeenSent = hasBeenSent;
    }

    private boolean hasBeenSent; // if the line has been given to the game
    // (to process or whatever)

    Line(String text) {
        this.text = text;
        this.hasBeenSent = false;
        this.color = new Color(0f, 1f, 0f, 1f);
    }

    Line (String text, boolean hasBeenSent) {
        this.text = text;
        this.hasBeenSent = hasBeenSent;
        this.color = new Color(0f, 1f, 0f, 1f);
    }

    Line (String text, boolean hasBeenSent, Color color) {
        this.text = text;
        this.hasBeenSent = hasBeenSent;
        this.color = color;
    }
}
