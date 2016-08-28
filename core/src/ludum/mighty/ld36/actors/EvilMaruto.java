package ludum.mighty.ld36.actors;


import ludum.mighty.ld36.actions.Action;

public class EvilMaruto extends BasicMaruto {

	public EvilMaruto()
	{
		super("evil_maruto_spreadsheet.png");
	}
	
	public EvilMaruto(String textureSheet) {
		super("evil_maruto_spreadsheet.png");
	}

	@Override
	public Action getNextAction() {
		// TODO : add AI code
		return null;
	}

}
