package ludum.mighty.ld36.textTerminal;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

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
        String[] actions = line.split(";");
        for (String action:actions) {
            String[] parts = action.split(" ");
            if(Arrays.asList(validcommands_zero).contains(parts[0].toLowerCase())) {
                // Valid command; zero additional parameters
                String command = parts[0].toLowerCase();
                if (parts.length != 1) {
                    // TODO : error "too many parameters"
                    return;
                }
                if(command.compareToIgnoreCase("walk") == 0) {
                    //Action walk = new Action(DefaultValues.ACTIONS.WALK);
                }
                //commands.add()
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
