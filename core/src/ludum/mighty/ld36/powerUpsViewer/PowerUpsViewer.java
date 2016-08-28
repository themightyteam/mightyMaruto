package ludum.mighty.ld36.powerUpsViewer;

import ludum.mighty.ld36.settings.DefaultValues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class PowerUpsViewer {

	private float boxStartPointX;
	private float boxStartPointY;
	private int boxWidth;
	private int boxHeigh;
	private int vMargin;
	private int hMargin;
	private Color backgroudColor;

	private ShapeRenderer shapeRenderer;

	private Texture texture;

	private TextureRegion textureBackground;
	private TextureRegion textureARRRGGGHHH;
	private TextureRegion textureYENDOR;
	private TextureRegion textureCHOCO;
	private TextureRegion textureGRENADE;
	private TextureRegion textureRANDOM;
	private TextureRegion textureSHIELD;
	private TextureRegion textureINVISIBILITY;
	private TextureRegion textureRING;
	private TextureRegion textureSONICBOMB;
	private TextureRegion textureDIAG_SONICBOMB;
	private TextureRegion textureSNEAKERS;
	private TextureRegion textureDIZZY;

	private DefaultValues.POWERUPS powerUps[];

	public PowerUpsViewer (Vector2 startPoint) {
		this.boxStartPointX = startPoint.x;
		this.boxStartPointY = startPoint.y;

		this.hMargin = 5;
		this.vMargin = 5;
		this.boxWidth = DefaultValues.TILESIZE * 2 + this.hMargin * 3;
		this.boxHeigh = DefaultValues.TILESIZE * 2 + this.vMargin * 3;

		this.backgroudColor = new Color(0f, 0f, 1f, .5f);

		this.shapeRenderer = new ShapeRenderer();

		this.texture = new Texture(Gdx.files.internal("items_spreadsheet.png"));

		int t = DefaultValues.TILESIZE;
		this.textureBackground = new TextureRegion(texture, 0, 0, t, t);
		this.textureARRRGGGHHH = new TextureRegion(texture, t * 3, t * 0, t, t);
		this.textureYENDOR = new TextureRegion(texture, t * 7, t * 0, t, t);
		this.textureCHOCO = new TextureRegion(texture, t * 2, t * 0, t, t);
		this.textureGRENADE = new TextureRegion(texture, t * 3, t * 1, t, t);
		this.textureRANDOM = new TextureRegion(texture, t * 1, t * 0, t, t);
		this.textureSHIELD = new TextureRegion(texture, t * 9, t * 0, t, t);
		this.textureINVISIBILITY = new TextureRegion(texture, t * 2, t * 1, t,
				t);
		this.textureRING = new TextureRegion(texture, t * 2, t * 2, t, t);
		this.textureSONICBOMB = new TextureRegion(texture, t * 7, t * 2, t, t);
		this.textureDIAG_SONICBOMB = new TextureRegion(texture, t * 8, t * 2,
				t, t);
		this.textureSNEAKERS = new TextureRegion(texture, t * 8, t * 0, t, t);
		this.textureDIZZY = new TextureRegion(texture, t * 3, t * 2, t, t);
		
		this.powerUps = new DefaultValues.POWERUPS[4];

		for (int i = 0; i < 4; i++) {
			this.powerUps[i] = null;
		}
	}

	public void setList(DefaultValues.POWERUPS pu0, DefaultValues.POWERUPS pu1,
			DefaultValues.POWERUPS pu2, DefaultValues.POWERUPS pu3) {
		this.powerUps[0] = pu0;
		this.powerUps[1] = pu1;
		this.powerUps[2] = pu2;
		this.powerUps[3] = pu3;
	}

	public void render(SpriteBatch batch) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);

		shapeRenderer.setColor(this.backgroudColor);
		shapeRenderer.rect(this.boxStartPointX, this.boxStartPointY,
				this.boxWidth, this.boxHeigh);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		batch.begin();
		batch.draw(this.textureBackground, this.boxStartPointX + this.hMargin,
				this.boxStartPointY + DefaultValues.TILESIZE + this.vMargin * 2,
				DefaultValues.TILESIZE,
				DefaultValues.TILESIZE);

		if (this.powerUps[0] != null) {
			batch.draw(getTexture(this.powerUps[0]), this.boxStartPointX
					+ this.hMargin, this.boxStartPointY
					+ DefaultValues.TILESIZE + this.vMargin * 2,
					DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		}

		batch.draw(this.textureBackground, this.boxStartPointX
 + DefaultValues.TILESIZE + this.hMargin * 2,
				this.boxStartPointY
				+ DefaultValues.TILESIZE + this.vMargin * 2,
				DefaultValues.TILESIZE, DefaultValues.TILESIZE);

		if (this.powerUps[1] != null) {
			batch.draw(getTexture(this.powerUps[1]), this.boxStartPointX
					+ DefaultValues.TILESIZE + this.hMargin * 2,
					this.boxStartPointY + DefaultValues.TILESIZE + this.vMargin
							* 2, DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		}
		
		batch.draw(this.textureBackground,
				this.boxStartPointX + this.hMargin,
				this.boxStartPointY + this.vMargin,
				DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		
		if (this.powerUps[2] != null) {
			batch.draw(getTexture(this.powerUps[2]), this.boxStartPointX + this.hMargin,
					this.boxStartPointY + this.vMargin,
					DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		}

		batch.draw(this.textureBackground, this.boxStartPointX + this.hMargin
				* 2 + DefaultValues.TILESIZE, this.boxStartPointY
				+ this.vMargin,
				DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		
		if (this.powerUps[3] != null) {
			batch.draw(getTexture(this.powerUps[3]), this.boxStartPointX + this.hMargin
					* 2 + DefaultValues.TILESIZE, this.boxStartPointY
					+ this.vMargin,
					DefaultValues.TILESIZE, DefaultValues.TILESIZE);
		}
		
		batch.end();
	}

	// ARRRGGGHHH, YENDOR, CHOCO, GRENADE, RANDOM, SHIELD, INVISIBILITY,
	// RING, SONICBOMB, DIAG_SONICBOMB, SNEAKERS, DIZZY,

	private TextureRegion getTexture(DefaultValues.POWERUPS pu) {
		switch (pu) {
		case ARRRGGGHHH:
			return this.textureARRRGGGHHH;
		case YENDOR:
			return this.textureYENDOR;
		case CHOCO:
			return this.textureCHOCO;
		case GRENADE:
			return this.textureGRENADE;
		case RANDOM:
			return this.textureRANDOM;
		case SHIELD:
			return this.textureSHIELD;
		case INVISIBILITY:
			return this.textureINVISIBILITY;
		case RING:
			return this.textureRING;
		case SONICBOMB:
			return this.textureSONICBOMB;
		case DIAG_SONICBOMB:
			return this.textureDIAG_SONICBOMB;
		case SNEAKERS:
			return this.textureSNEAKERS;
		case DIZZY:
			return this.textureDIZZY;
		default:
			break;
		}
		return null;
	}
}
