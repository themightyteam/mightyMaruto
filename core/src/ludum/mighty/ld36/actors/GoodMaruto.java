package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.textTerminal.CommandProcessor;

public class GoodMaruto extends BasicMaruto {

	public GoodMaruto(CommandProcessor cm)
	{
		this(cm, null);
	}
	
	public GoodMaruto(CommandProcessor cm, String textureSheet) {
		super(cm, "evil_maruto_spreadsheet.png");
	}

}
