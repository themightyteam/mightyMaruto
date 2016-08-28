package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.actions.Action;

public class GoodMaruto extends BasicMaruto {

	public GoodMaruto()
	{
		this("maruto_spreadsheet.png");
	}
	
	public GoodMaruto(String textureSheet) {
		super("maruto_spreadsheet.png");

	}

	@Override
	public Action getNextAction() {
		if (nextAction != null) {
			return nextAction;
		} else {
			return null;
		}
	}
}
