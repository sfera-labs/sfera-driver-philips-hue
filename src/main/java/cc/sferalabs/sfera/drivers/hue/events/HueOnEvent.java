package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when a light switches on or off.
 * 
 * @sfera.event_id light(&lt;id&gt;).on where &lt;id&gt; is the light ID
 * @sfera.event_val true the light switched on
 * @sfera.event_val false the light switched off
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public class HueOnEvent extends BooleanEvent implements HueLighEvent {

	public HueOnEvent(Node source, PHLight light, Boolean value) {
		super(source, "light(" + light.getIdentifier() + ").on", value);
	}

}
