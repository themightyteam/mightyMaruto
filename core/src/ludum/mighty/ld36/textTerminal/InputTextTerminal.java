package ludum.mighty.ld36.textTerminal;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by dchaves on 27/08/16.
 */
public interface InputTextTerminal extends InputProcessor {
    public String getOldestUnprocessedLine();
}
