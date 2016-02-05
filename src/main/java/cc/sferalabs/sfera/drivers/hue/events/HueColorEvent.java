package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.drivers.hue.Color;
import cc.sferalabs.sfera.events.BaseEvent;
import cc.sferalabs.sfera.events.Node;

public class HueColorEvent extends BaseEvent implements HueLighEvent {

	private final Color color;

	public HueColorEvent(Node source, PHLight light, Color color) {
		super(source, "light(" + light.getIdentifier() + ").color");
		this.color = color;
	}

	@Override
	public Color getValue() {
		return color;
	}
}
