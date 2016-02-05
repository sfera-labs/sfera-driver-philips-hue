package cc.sferalabs.sfera.drivers.hue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cc.sferalabs.sfera.core.Configuration;
import cc.sferalabs.sfera.drivers.hue.events.HueAuthenticationEvent;
import cc.sferalabs.sfera.events.Bus;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

public class BridgeListener implements PHSDKListener {

	private final Logger logger;
	private final PHHueSDK phHueSDK;
	private final Hue driver;
	private final String username;
	private final Configuration configuration;

	/**
	 * 
	 * @param id
	 * @param phHueSDK
	 */
	public BridgeListener(Hue driver, PHHueSDK phHueSDK,
			Configuration configuration) {
		this.logger = LogManager.getLogger(getClass().getName() + "."
				+ driver.getId());
		this.phHueSDK = phHueSDK;
		this.driver = driver;
		this.username = getUsername();
		this.configuration = configuration;
	}

	/**
	 * 
	 * @return
	 */
	private String getUsername() {
		StringBuilder username = new StringBuilder(driver.getId().toLowerCase());
		while (username.length() < 20) {
			username.append("X");
		}
		return username.substring(0, 20);
	}

	@Override
	public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
		PHAccessPoint myAP = null;
		for (PHAccessPoint accessPoint : accessPoints) {
			logger.debug("Found AP - MAC: {}, IP: {}, User: {}",
					accessPoint.getMacAddress(), accessPoint.getIpAddress(),
					accessPoint.getUsername());
			String macAddress = configuration.get("mac", null);
			if (macAddress == null
					|| macAddress.equalsIgnoreCase(accessPoint.getMacAddress())) {
				myAP = accessPoint;
				break;
			}
		}
		if (myAP != null) {
			myAP.setUsername(username);
			logger.info("Connecting to AP '{}'...", myAP.getMacAddress());
			phHueSDK.connect(myAP);
		}
	}

	@Override
	public void onAuthenticationRequired(PHAccessPoint accessPoint) {
		logger.debug(
				"Authentication Required on AP - MAC: {}, IP: {}, User: {}",
				accessPoint.getMacAddress(), accessPoint.getIpAddress(),
				accessPoint.getUsername());
		phHueSDK.startPushlinkAuthentication(accessPoint);
		Bus.postIfChanged(new HueAuthenticationEvent(driver, true));
	}

	@Override
	public void onCacheUpdated(List<Integer> cacheNotificationsList,
			PHBridge bridge) {
		logger.debug("Cache Update");
		if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
			driver.updateLigthsState();
		}
	}

	@Override
	public void onBridgeConnected(PHBridge bridge) {
		phHueSDK.setSelectedBridge(bridge);
		Integer interval = configuration.get("interval", null);
		if (interval == null) {
			interval = PHHueSDK.HB_INTERVAL;
		} else {
			interval *= 1000;
		}
		phHueSDK.enableHeartbeat(bridge, interval);

		driver.setBridge(bridge);
	}

	@Override
	public void onConnectionResumed(PHBridge bridge) {
	}

	@Override
	public void onConnectionLost(PHAccessPoint accessPoint) {
		logger.warn("Connection lost");
		driver.quit();
	}

	@Override
	public void onError(int code, final String message) {
		logger.warn("Listner error: {} - {}", code, message);
		if (code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
			Bus.postIfChanged(new HueAuthenticationEvent(driver, false));
		}
	}

	@Override
	public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
		logger.warn("Parsing Errors");
	}

}
