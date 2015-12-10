package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

public class HueOnEvent extends BooleanEvent implements HueLighEvent {

	public HueOnEvent(Node source, PHLight light, Boolean value) {
		super(source, "light(" + light.getIdentifier() + ").on", value);
	}

}
