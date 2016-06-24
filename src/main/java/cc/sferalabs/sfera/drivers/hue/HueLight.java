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

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

/**
 * Class representing a Hue light.
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
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
	HueLight(Hue driver, PHBridge bridge, String lightId) {
		this.driver = driver;
		this.bridge = bridge;
		this.lightId = lightId;
	}

	/**
	 * 
	 * @return the light ID
	 */
	public String getId() {
		return lightId;
	}

	/**
	 * Sends an update request to set the light on or off.
	 * 
	 * @param on
	 *            {@code true} for on, {@code false} for off
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setOn(boolean on) {
		PHLightState lightState = new PHLightState();
		lightState.setOn(on);
		return driver.sendStateUpdate(lightId, lightState);
	}

	/**
	 * Sends an update request to set the brightness of the light to the
	 * specified value.
	 * 
	 * @param value
	 *            hue brightness to set (1-254)
	 * @return {@code true} if the request is successful, {@code false}
	 *         otherwise
	 */
	public boolean setBrightness(int value) {
		PHLightState lightState = new PHLightState();
		lightState.setBrightness(value);
		return driver.sendStateUpdate(lightId, lightState);
	}

	/**
	 * @return the light color
	 */
	public Color getColor() {
		PHLight l = bridge.getResourceCache().getLights().get(lightId);
		if (l == null) {
			return null;
		}
		return new Color(driver, l, l.getLastKnownLightState());
	}

}
