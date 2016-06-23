package cc.sferalabs.sfera.drivers.hue.events;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when the driver connects to the Hue bridge for the first time
 * and authentication is required.
 * 
 * @sfera.event_id authentication
 * @sfera.event_val true authentication procedure required and ready to be performed
 * @sfera.event_val false authentication procedure not required or failed
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public class HueAuthenticationEvent extends BooleanEvent implements HueEvent {

	public HueAuthenticationEvent(Node source, Boolean value) {
		super(source, "authentication", value);
	}

}
