package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

public class HueReachableEvent extends BooleanEvent implements HueLighEvent {

	public HueReachableEvent(Node source, PHLight light, Boolean value) {
		super(source, "light(" + light.getIdentifier() + ").reachable", value);
	}

}
