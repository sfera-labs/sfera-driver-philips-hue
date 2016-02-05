package cc.sferalabs.sfera.drivers.hue.events;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

public class HueConnectedEvent extends BooleanEvent implements HueEvent {

	public HueConnectedEvent(Node source, boolean value) {
		super(source, "connected", value);
	}

}
