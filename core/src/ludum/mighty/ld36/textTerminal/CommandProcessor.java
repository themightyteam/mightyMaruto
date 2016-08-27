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
    private static final String[] validcommands_zero = {"walk", "moonwalk", "run", "punch", "stop", "help"};
    private static final String[] validcommands_one = {"rotate", "drop", "shoot"};
    private static final String[] powerups = {"arrrggghhh", "yendor", "choco", "grenade", "random" };
    private static final String[] directions = {"left","right"};

    
    // Parse string to get action or list of actions
    public void next(String line) {
    	
        Action todo = null;

        String[] actions = line.split(";");
        for (String action:actions) {
            String[] parts = action.split(" ");
            String command = parts[0].toLowerCase();
            if(Arrays.asList(validcommands_zero).contains(parts[0].toLowerCase())) {
                // Valid command; zero additional parameters
                if (parts.length != 1) {
                    // TODO : error "too many parameters"
                    return;
                }
                if(command.compareToIgnoreCase("walk") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.WALK);
                } else if (command.compareToIgnoreCase("moonwalk") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.MOONWALK);
                } else if (command.compareToIgnoreCase("run") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.RUN);
                } else if (command.compareToIgnoreCase("punch") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.PUNCH);
                } else if (command.compareToIgnoreCase("stop") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.STOP);
                } else if (command.compareToIgnoreCase("help") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.HELP);
                }
            } else if (Arrays.asList(validcommands_one).contains(parts[0].toLowerCase())) {
                // Valid command; one additional parameter
                if (parts.length != 2) {
                    // TODO : error "invalid parameter number"
                    return;
                }
                if(command.compareToIgnoreCase("rotate") == 0) {
                    if (parts[1].compareToIgnoreCase("left") == 0) {
                        todo = new Action(DefaultValues.ACTIONS.ROTATE,DefaultValues.RELATIVE_ROTATIONS.LEFT);
                    } else if (parts[1].compareToIgnoreCase("right") == 0){
                        todo = new Action(DefaultValues.ACTIONS.ROTATE,DefaultValues.RELATIVE_ROTATIONS.RIGHT);
                    } else {
                        // TODO : invalid direction
                        return;
                    }
                } else if (command.compareToIgnoreCase("drop") == 0) {
                    if (parts[1].compareToIgnoreCase("arrrggghhh") == 0) {
                        todo = new Action(DefaultValues.ACTIONS.DROP, DefaultValues.POWERUPS.ARRRGGGHHH);
                    } else if (parts[1].compareToIgnoreCase("yendor") == 0){
                        todo = new Action(DefaultValues.ACTIONS.DROP, DefaultValues.POWERUPS.YENDOR);
                    } else if (parts[1].compareToIgnoreCase("choco") == 0){
                        todo = new Action(DefaultValues.ACTIONS.DROP, DefaultValues.POWERUPS.CHOCO);
                    } else if (parts[1].compareToIgnoreCase("grenade") == 0){
                        todo = new Action(DefaultValues.ACTIONS.DROP, DefaultValues.POWERUPS.GRENADE);
                    } else if (parts[1].compareToIgnoreCase("random") == 0){
                        todo = new Action(DefaultValues.ACTIONS.DROP, DefaultValues.POWERUPS.RANDOM);
                    }
                } else if (command.compareToIgnoreCase("shoot") == 0) {
                    if (parts[1].compareToIgnoreCase("arrrggghhh") == 0) {
                        todo = new Action(DefaultValues.ACTIONS.SHOOT, DefaultValues.POWERUPS.ARRRGGGHHH);
                    } else if (parts[1].compareToIgnoreCase("yendor") == 0){
                        todo = new Action(DefaultValues.ACTIONS.SHOOT, DefaultValues.POWERUPS.YENDOR);
                    } else if (parts[1].compareToIgnoreCase("choco") == 0){
                        todo = new Action(DefaultValues.ACTIONS.SHOOT, DefaultValues.POWERUPS.CHOCO);
                    } else if (parts[1].compareToIgnoreCase("grenade") == 0){
                        todo = new Action(DefaultValues.ACTIONS.SHOOT, DefaultValues.POWERUPS.GRENADE);
                    } else if (parts[1].compareToIgnoreCase("random") == 0){
                        todo = new Action(DefaultValues.ACTIONS.SHOOT, DefaultValues.POWERUPS.RANDOM);
                    }
                }
            } else {
                // TODO : invalid command
                return;
            }

            if (todo != null) {
                commands.add(todo);
            }
        }
    }

    public Action getNextAction () {
        Action next = commands.firstElement();
        commands.remove(next);
        return next;
    }
}
