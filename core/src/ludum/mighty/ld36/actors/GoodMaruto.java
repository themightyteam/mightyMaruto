package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.textTerminal.CommandProcessor;

public class GoodMaruto extends BasicMaruto {

	public GoodMaruto(CommandProcessor cm)
	{
		this(cm, "maruto_spreadsheet.png");
	}
	
	public GoodMaruto(CommandProcessor cm, String textureSheet) {
		super(cm, "maruto_spreadsheet.png");
	}

	@Override
	public DefaultValues.ACTIONS getNextAction() {
		if (nextAction != null) {
			return nextAction.gettype();
		} else {
			return null;
		}
	}
}
