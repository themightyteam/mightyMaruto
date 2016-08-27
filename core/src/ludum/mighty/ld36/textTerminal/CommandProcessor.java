package ludum.mighty.ld36.textTerminal;

import ludum.mighty.ld36.actors.Action;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by dchaves on 27/08/16.
 */

public class CommandProcessor {
    private Vector<Action> commands;
    private boolean requiresuserinput;
    private static final String[] validcommands_zero = {"walk", "moonwalk", "run", "punch", "stop", "help"};
    private static final String[] validcommands_one = {"rotate", "drop", "shoot"};
    private static final String[] powerups = {"arrrggghhh", "yendor", "choco", "grenade", "random" };
    private static final String[] directions = {"left","right"};

    public void next(String line) {
        // TODO: parse new received line
        String[] actions = line.split(";");
        for (String action:actions) {
            String[] parts = action.split(" ");
            if(Arrays.asList(validcommands_zero).contains(parts[0].toLowerCase())) {
                // Valid command; zero additional parameters
                String command = parts[0].toLowerCase();
                if (parts.length != 1) {
                    // TODO : error "too many parameters"
                }
            } else if (Arrays.asList(validcommands_one).contains(parts[0].toLowerCase())) {
                // Valid command; one additional parameter
                if (parts.length != 2) {
                    // TODO : error "invalid parameter number"
                }
            } else {
                // TODO : invalid command
            }
        }
    }
}
