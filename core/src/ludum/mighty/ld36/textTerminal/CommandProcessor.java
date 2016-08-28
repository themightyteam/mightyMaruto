package ludum.mighty.ld36.textTerminal;

import com.badlogic.gdx.graphics.Color;
import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 * Created by dchaves on 27/08/16.
 */

public class CommandProcessor {
    private Vector<Action> commands;
    private static final String[] validcommands_zero = {"walk", "moonwalk", "run", "punch", "stop", "help"};
    private static final String[] validcommands_one = {"turn", "drop", "shoot"};
    private static final String[] powerups = {"arrrggghhh", "yendor", "choco", "grenade", "random" };
    private static final String[] directions = {"left","right"};

    private static final Color COLOR_RESPONSE = new Color(0f, .8f, 0f, 1f);
    private static final Color COLOR_ERROR = new Color(1f, 0f, 0f, 1f);

    public CommandProcessor() {
        this.commands = new Vector<Action>();
    }

    // Parse string to get action or list of actions
    public Line next(String line) {

        Action todo = null;
        Vector<Action> tempcommands = new Vector<Action>();

        String[] actions = line.split(";");
        for (String action:actions) {
            String[] parts = action.trim().split(" ");
            String command = parts[0].toLowerCase();
            if(Arrays.asList(validcommands_zero).contains(parts[0].toLowerCase())) {
                // Valid command; zero additional parameters
                if (parts.length != 1) {
                    // TODO : error "too many parameters"
                    return new Line(getRandomString(DefaultValues.ERRORS),true,COLOR_ERROR);
                }
                if(command.compareToIgnoreCase("walk") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.WALK);
                } else if (command.compareToIgnoreCase("moonwalk") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.MOONWALK);
                } else if (command.compareToIgnoreCase("run") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.RUN);
                } else if (command.compareToIgnoreCase("punch") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.SHOOT, DefaultValues.POWERUPS.PUNCH);
                } else if (command.compareToIgnoreCase("stop") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.STOP);
                } else if (command.compareToIgnoreCase("help") == 0) {
                    todo = new Action(DefaultValues.ACTIONS.HELP);
                }

                //System.out.println("---> " + todo);
            } else if (Arrays.asList(validcommands_one).contains(parts[0].toLowerCase())) {
                // Valid command; one additional parameter
                if (parts.length != 2) {
                    // TODO : error "invalid parameter number"
                    return new Line(getRandomString(DefaultValues.ERRORS),true,COLOR_ERROR);
                }
                if (command.compareToIgnoreCase("turn") == 0) {
                    if (parts[1].compareToIgnoreCase("left") == 0) {
                        todo = new Action(DefaultValues.ACTIONS.TURN,DefaultValues.RELATIVE_ROTATIONS.LEFT);
                    } else if (parts[1].compareToIgnoreCase("right") == 0){
                        todo = new Action(DefaultValues.ACTIONS.TURN,DefaultValues.RELATIVE_ROTATIONS.RIGHT);
                    } else {
                        // TODO : invalid direction
                        return new Line(getRandomString(DefaultValues.ERRORS),true,COLOR_ERROR);
                    }
                    //System.out.println("---> " + todo);
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

                    //System.out.println("---> " + todo);
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

                    //System.out.println("---> " + todo);
                }
            } else {
                // TODO : invalid command
                return new Line(getRandomString(DefaultValues.ERRORS),true,COLOR_ERROR);
            }
            if (todo != null) {
                tempcommands.add(todo);
            }
        }
        this.commands.addAll(tempcommands);
        String toprint = "";
        for (Action command:tempcommands) {
            toprint += command + " then ";
        }
        toprint = toprint.substring(0,toprint.lastIndexOf(" then "));

        return new Line(toprint, true, COLOR_RESPONSE);
    }

    public Action getNextAction () {
    	if (commands.size() == 0) return null;
        //System.out.println(commands.size());
        Action next = commands.firstElement();
        //System.out.println(next);
        commands.remove(next);
        //System.out.println(commands.size());
        return next;
    }

    private String getRandomString (String[] library) {
        int idx = new Random().nextInt(library.length);
        String random = (library[idx]);
        return random;
    }
}
