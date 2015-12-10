package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.events.Node;
import cc.sferalabs.sfera.events.NumberEvent;

public class HueBrightnessEvent extends NumberEvent implements HueLighEvent {

	public HueBrightnessEvent(Node source, PHLight light, Integer value) {
		super(source, "light(" + light.getIdentifier() + ").brightness", value);
	}

}
