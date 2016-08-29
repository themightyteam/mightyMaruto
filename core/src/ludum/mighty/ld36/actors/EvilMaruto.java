package ludum.mighty.ld36.actors;



public class EvilMaruto extends BasicMaruto {

	public EvilMaruto(int maruto_number) {

		this();
		name = "Evil Maruto " + Integer.toString(maruto_number);
	}
	
	public EvilMaruto()
	{
		this("evil_maruto_spreadsheet.png");
	}
	
	public EvilMaruto(String textureSheet) {
		super("evil_maruto_spreadsheet.png");

		name = "Evil Maruto";
	}



}
