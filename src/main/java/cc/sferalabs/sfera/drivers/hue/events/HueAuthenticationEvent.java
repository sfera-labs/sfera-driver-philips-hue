package cc.sferalabs.sfera.drivers.hue.events;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

public class HueAuthenticationEvent extends BooleanEvent implements HueEvent {

	public HueAuthenticationEvent(Node source, Boolean value) {
		super(source, "authentication", value);
	}

}
