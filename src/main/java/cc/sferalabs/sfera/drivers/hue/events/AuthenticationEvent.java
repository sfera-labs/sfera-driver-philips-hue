package cc.sferalabs.sfera.drivers.hue.events;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

public class AuthenticationEvent extends BooleanEvent implements HueEvent {

	public AuthenticationEvent(Node source, Boolean value) {
		super(source, "authentication", value);
	}

}
