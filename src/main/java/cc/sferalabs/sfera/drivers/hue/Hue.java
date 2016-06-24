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

import java.util.List;
import java.util.Map;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import cc.sferalabs.sfera.core.Configuration;
import cc.sferalabs.sfera.drivers.Driver;
import cc.sferalabs.sfera.drivers.hue.events.HueAuthenticationEvent;
import cc.sferalabs.sfera.drivers.hue.events.HueBrightnessEvent;
import cc.sferalabs.sfera.drivers.hue.events.HueColorEvent;
import cc.sferalabs.sfera.drivers.hue.events.HueOnEvent;
import cc.sferalabs.sfera.drivers.hue.events.HueReachableEvent;
import cc.sferalabs.sfera.events.Bus;

public class Hue extends Driver {

	private static final long SEARCH_TIMEOUT = 10000;
	private static final long AUTHENTICATION_TIMEOUT = 30000;

	private BridgeListener listener;
	private PHBridge bridge;
	private PHHueSDK phHueSDK;

	private boolean updateResult;

	public Hue(String id) {
		super(id);
	}

	@Override
	protected boolean onInit(Configuration configuration) throws InterruptedException {
		phHueSDK = PHHueSDK.create();
		phHueSDK.setAppName("cc.sferalabs.sfera.drivers.hue");
		phHueSDK.setDeviceName("Sfera");

		listener = new BridgeListener(this, phHueSDK, configuration);
		phHueSDK.getNotificationManager().registerSDKListener(listener);

		PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK
				.getSDKService(PHHueSDK.SEARCH_BRIDGE);
		sm.search(true, true);

		long end = System.currentTimeMillis() + SEARCH_TIMEOUT + AUTHENTICATION_TIMEOUT + 5000;
		while (bridge == null && System.currentTimeMillis() < end) {
			Thread.sleep(100);
		}

		Bus.postIfChanged(new HueAuthenticationEvent(this, false));

		if (bridge == null) {
			log.error("Bridge not found");
			return false;
		}

		log.info("Connected to bridge");

		for (PHLight light : bridge.getResourceCache().getAllLights()) {
			log.debug("Found light {} {} {}", light.getIdentifier(),
					light.getLightType().toString(), light.getName());
		}

		updateLigthsState();

		return true;
	}

	/**
	 * 
	 * @param light
	 */
	void updateLigthsState() {
		for (PHLight light : bridge.getResourceCache().getAllLights()) {
			PHLightState state = light.getLastKnownLightState();
			Bus.postIfChanged(new HueOnEvent(this, light, state.isOn()));
			Bus.postIfChanged(new HueReachableEvent(this, light, state.isReachable()));
			Bus.postIfChanged(new HueBrightnessEvent(this, light, state.getBrightness()));
			Color color = new Color(this, light, state);
			Bus.postIfChanged(new HueColorEvent(this, light, color));
		}
	}

	@Override
	protected boolean loop() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
		return true;
	}

	@Override
	protected void onQuit() {
		bridge = null;
		if (phHueSDK != null) {
			phHueSDK.disableAllHeartbeat();
			phHueSDK.destroySDK();
		}
	}

	/**
	 * 
	 * @param bridge
	 */
	void setBridge(PHBridge bridge) {
		this.bridge = bridge;
	}

	/**
	 * Returns the light with the specified ID.
	 * 
	 * @param id
	 *            the ID of the light to be returned
	 * @return the light with the specified ID
	 */
	public HueLight light(int id) {
		return light(String.valueOf(id));
	}

	/**
	 * Returns the light with the specified ID.
	 * 
	 * @param id
	 *            the ID of the light to be returned
	 * @return the light with the specified ID
	 */
	public HueLight light(String id) {
		if (bridge == null) {
			return null;
		}
		return new HueLight(this, bridge, id);
	}

	/**
	 * 
	 * @param lightId
	 * @param lightState
	 * @return
	 */
	synchronized boolean sendStateUpdate(String lightId, PHLightState lightState) {
		log.debug("Updating state...");
		final Hue instance = this;
		updateResult = false;
		bridge.updateLightState(lightId, lightState, new PHLightListener() {

			@Override
			public void onSuccess() {
			}

			@Override
			public void onStateUpdate(Map<String, String> successAttribute,
					List<PHHueError> errorAttribute) {
				updateResult = true;
				synchronized (instance) {
					instance.notify();
				}
				log.debug("State updated");
			}

			@Override
			public void onError(int code, String message) {
				log.warn("Update error: {} - {}", code, message);
				updateResult = false;
				try {
					synchronized (instance) {
						instance.notify();
					}
				} catch (Exception e) {
				}
			}

			@Override
			public void onSearchComplete() {
			}

			@Override
			public void onReceivingLights(List<PHBridgeResource> lights) {
			}

			@Override
			public void onReceivingLightDetails(PHLight light) {
			}
		});

		log.debug("Waiting update");

		try {
			wait(3000);
		} catch (InterruptedException e) {
		}

		log.debug("State update result: {}", updateResult);

		return updateResult;
	}
}
