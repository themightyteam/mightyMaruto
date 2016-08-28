package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.actions.Action;
import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemBlackBox extends Actor_Powerup {

	private Texture texture;
	private TextureRegion textureRegion;

	public ItemBlackBox() {
		this.texture = new Texture(Gdx.files.internal("items_spreadsheet.png"));
		this.textureRegion = new TextureRegion(texture, 0,
				DefaultValues.TILESIZE, DefaultValues.TILESIZE,
				DefaultValues.TILESIZE);
	}

	@Override
	public void doMovement(Action action) {
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(this.textureRegion, getX(), getY());
	}
}
