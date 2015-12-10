package cc.sferalabs.sfera.drivers.hue;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class Color {

	private final Hue driver;
	private final PHLight light;
	private final Float x;
	private final Float y;
	private final Integer saturation;
	private final Integer hue;
	private final Integer temperature;
	private final String description;

	/**
	 * 
	 * @param driver
	 * @param light
	 * @param state
	 */
	public Color(Hue driver, PHLight light, PHLightState state) {
		this.driver = driver;
		this.light = light;
		this.x = state.getX();
		this.y = state.getY();
		this.saturation = state.getSaturation();
		this.hue = state.getHue();
		this.temperature = state.getCt();
		StringBuilder sb = new StringBuilder();
		sb.append("{\"x\":").append(x);
		sb.append(",\"y\":").append(y);
		sb.append(",\"saturation\":").append(saturation);
		sb.append(",\"hue\":").append(hue);
		sb.append(",\"temperature\":").append(temperature);
		sb.append('}');
		this.description = sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	public PHLight getLight() {
		return light;
	}

	@Override
	public String toString() {
		return description;
	}

	@Override
	public boolean equals(Object color) {
		if (color == null) {
			return false;
		}

		if (!(color instanceof Color)) {
			return false;
		}

		return toString().equals(color.toString());
	}

	/**
	 * @return the x value
	 */
	public Float getX() {
		return x;
	}

	/**
	 * @return the y value
	 */
	public Float getY() {
		return y;
	}

	/**
	 * @return the saturation
	 */
	public Integer getSaturation() {
		return saturation;
	}

	/**
	 * @return the hue
	 */
	public Integer getHue() {
		return hue;
	}

	/**
	 * @return the temperature
	 */
	public Integer getTemperature() {
		return temperature;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean setXy(float[] value) {
		PHLightState lightState = new PHLightState();
		lightState.setX(value[0]);
		lightState.setY(value[1]);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean setRgb(int[] value) {
		float xy[] = PHUtilities.calculateXYFromRGB(value[0], value[1],
				value[2], light.getModelNumber());
		return setXy(xy);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean setHue(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setHue(value);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean setSaturation(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setSaturation(value);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean setTemperature(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setCt(value);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}
	
}
