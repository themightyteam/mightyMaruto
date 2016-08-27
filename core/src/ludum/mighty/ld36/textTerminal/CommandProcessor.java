package ludum.mighty.ld36.textTerminal;

/**
 * Created by dchaves on 27/08/16.
 */

public class CommandProcessor {
    private InputTextTerminal inputText;
    public CommandProcessor(InputTextTerminal itTerminal) {
        this.inputText = itTerminal;
    }

    public String getMessage() {
        return "";
    }

    public String getAction() {
        return "";
    }

    public void done() {

    }
}
