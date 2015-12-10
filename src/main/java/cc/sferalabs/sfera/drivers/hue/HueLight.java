package cc.sferalabs.sfera.drivers.hue;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class HueLight {

	private final Hue driver;
	private final PHBridge bridge;
	private final String lightId;

	/**
	 * 
	 * @param driver
	 * @param bridge
	 * @param lightId
	 */
	public HueLight(Hue driver, PHBridge bridge, String lightId) {
		this.driver = driver;
		this.bridge = bridge;
		this.lightId = lightId;
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return lightId;
	}

	/**
	 * 
	 * @param on
	 * @return
	 */
	public boolean setOn(boolean on) {
		PHLightState lightState = new PHLightState();
		lightState.setOn(on);
		return driver.sendStateUpdate(lightId, lightState);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean setBrightness(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setBrightness(value);
		return driver.sendStateUpdate(lightId, lightState);
	}

	/**
	 * 
	 * @return
	 */
	public Color getColor() {
		PHLight l = bridge.getResourceCache().getLights().get(lightId);
		if (l == null) {
			return null;
		}
		return new Color(driver, l, l.getLastKnownLightState());
	}

}
