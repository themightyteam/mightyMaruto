package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.textTerminal.CommandProcessor;

public class EvilMaruto extends BasicMaruto {

	public EvilMaruto(CommandProcessor cm)
	{
		super(cm, null);
	}
	
	public EvilMaruto(CommandProcessor cm, String textureSheet) {
		super(cm, "evil_maruto_spreadsheet.png");
	}

}
