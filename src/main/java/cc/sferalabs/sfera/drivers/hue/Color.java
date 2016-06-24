/*-
 * +======================================================================+
 * Hue
 * ---
 * Copyright (C) 2016 Sfera Labs S.r.l.
 * ---
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * -======================================================================-
 */

package cc.sferalabs.sfera.drivers.hue;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

/**
 * Class representing a light color.
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public class Color {

	private final Hue driver;
	private final PHLight light;
	private final Float x;
	private final Float y;
	private final Integer saturation;
	private final Integer hue;
	private final Integer temperature;
	private final String rgb;

	/**
	 * 
	 * @param driver
	 * @param light
	 * @param state
	 */
	Color(Hue driver, PHLight light, PHLightState state) {
		this.driver = driver;
		this.light = light;
		this.x = state.getX();
		this.y = state.getY();
		this.saturation = state.getSaturation();
		this.hue = state.getHue();
		this.temperature = state.getCt();
		this.rgb = computeRgb(state);
	}

	/**
	 * @param state
	 * @return
	 */
	private String computeRgb(PHLightState state) {
		float h = state.getHue() / 65535f;
		float s = state.getSaturation() / 254f;
		float br = state.getBrightness() / 254f;
		int rgb = java.awt.Color.HSBtoRGB(h, s, br);
		int r = ((rgb >>> 16) & 0xFF);
		int g = ((rgb >>> 8) & 0xFF);
		int b = (rgb & 0xFF);
		StringBuilder ret = new StringBuilder("#");
		if (r < 0x10) {
			ret.append(0);
		}
		ret.append(Integer.toString(r, 16));
		if (g < 0x10) {
			ret.append(0);
		}
		ret.append(Integer.toString(g, 16));
		if (b < 0x10) {
			ret.append(0);
		}
		ret.append(Integer.toString(b, 16));
		return ret.toString();
	}

	@Override
	public String toString() {
		return rgb;
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
	 * @return the RGB value
	 */
	public String getRgb() {
		return rgb;
	}

	/**
	 * Sends an update request to set the x and y values of the color to the
	 * specified value.
	 * 
	 * @param value
	 *            array of floating values where the one at index 0 represents
	 *            the x value and the one at index 1 represents the y value to
	 *            set
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setXy(float[] value) {
		PHLightState lightState = new PHLightState();
		lightState.setX(value[0]);
		lightState.setY(value[1]);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}

	/**
	 * Sends an update request to set the hue of the color to the specified
	 * value.
	 * 
	 * @param value
	 *            hue value to set (0-65535)
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setHue(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setHue(value);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}

	/**
	 * Sends an update request to set the saturation of the color to the
	 * specified value.
	 * 
	 * @param value
	 *            saturation value to set (0-254)
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setSaturation(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setSaturation(value);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}

	/**
	 * Sends an update request to set the temperature of the color to the
	 * specified value.
	 * 
	 * @param value
	 *            temperature value to set
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setTemperature(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setCt(value);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}

	/**
	 * Sends an update request to set the RGB value of the color to the
	 * specified value.
	 * 
	 * @param rgb
	 *            array of int values where the one at index 0 represents the
	 *            red-component value, the one at index 1 represents the
	 *            green-component value and the one at index 2 represents the
	 *            blue-component value to set
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setRgb(int[] rgb) {
		float[] hsb = java.awt.Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
		return setHsb(new int[] { Math.round(hsb[0] * 65535), Math.round(hsb[1] * 254),
				Math.round(hsb[2] * 254) });
	}

	/**
	 * Sends an update request to set the RGB value of the color to the
	 * specified value.
	 * 
	 * @param rgb
	 *            String representation of the hexadecimal RGB value to set
	 *            (e.g. "#FF11BB" or "FF11BB")
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setRgb(String rgb) {
		if (rgb.startsWith("#")) {
			rgb = rgb.substring(1);
		}
		int r = Integer.parseInt(rgb.substring(0, 2), 16);
		int g = Integer.parseInt(rgb.substring(2, 4), 16);
		int b = Integer.parseInt(rgb.substring(4, 6), 16);
		return setRgb(new int[] { r, g, b });
	}

	/**
	 * Sends an update request to set the hue, saturation and brightness values
	 * of the color to the specified values.
	 * 
	 * @param hsb
	 *            array of int values where the one at index 0 represents the
	 *            hue value, the one at index 1 represents the saturation value
	 *            and the one at index 2 represents the brightness value to set
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setHsb(int[] hsb) {
		PHLightState lightState = new PHLightState();
		lightState.setHue(hsb[0]);
		lightState.setSaturation(hsb[1]);
		lightState.setBrightness(hsb[2]);
		return driver.sendStateUpdate(light.getIdentifier(), lightState);
	}

}
