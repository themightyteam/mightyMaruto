package ludum.mighty.ld36.actors;

import ludum.mighty.ld36.settings.DefaultValues;
import ludum.mighty.ld36.settings.DefaultValues.POWERUPS;

public class Item_Powerup {

	String name;
	private boolean canbedropped = DefaultValues.POWERUP_CAN_BE_DROPPED;
	POWERUPS type;
	int duration;
	int speedPowerup;
	int strengthPowerup;
	boolean invisibilityPowerup;
	boolean dizzyPowerup;
	boolean invinciblePowerup;

	public Item_Powerup(POWERUPS type, int duration, int speedPowerup,
			int strengthPowerup, boolean invisibilityPowerup,
			boolean dizzyPowerup, boolean invinciblePowerup) {
		this.type = type;
		this.name = type.toString();
		this.duration = duration;
		this.speedPowerup = speedPowerup;
		this.invisibilityPowerup = invisibilityPowerup;
		this.dizzyPowerup = dizzyPowerup;
		this.invinciblePowerup = invinciblePowerup;
	}

	public boolean candrop() {
		return this.canbedropped;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanbedropped() {
		return canbedropped;
	}

	public void setCanbedropped(boolean canbedropped) {
		this.canbedropped = canbedropped;
	}

	public POWERUPS getType() {
		return type;
	}

	public void setType(POWERUPS type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getSpeedPowerup() {
		return speedPowerup;
	}

	public void setSpeedPowerup(int speedPowerup) {
		this.speedPowerup = speedPowerup;
	}

	public int getStrengthPowerup() {
		return strengthPowerup;
	}

	public void setStrengthPowerup(int strengthPowerup) {
		this.strengthPowerup = strengthPowerup;
	}

	public boolean isInvisibilityPowerup() {
		return invisibilityPowerup;
	}

	public void setInvisibilityPowerup(boolean invisibilityPowerup) {
		this.invisibilityPowerup = invisibilityPowerup;
	}

	public boolean isDizzyPowerup() {
		return dizzyPowerup;
	}

	public void setDizzyPowerup(boolean dizzyPowerup) {
		this.dizzyPowerup = dizzyPowerup;
	}

	public boolean isInvinciblePowerup() {
		return invinciblePowerup;
	}

	public void setInvinciblePowerup(boolean invinciblePowerup) {
		this.invinciblePowerup = invinciblePowerup;
	}

}
