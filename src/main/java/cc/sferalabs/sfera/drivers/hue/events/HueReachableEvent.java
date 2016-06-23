package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when a light loses or gains connectivity with the bridge.
 * 
 * @sfera.event_id light(&lt;id&gt;).reachable where &lt;id&gt; is the light ID
 * @sfera.event_val true the light is reachable
 * @sfera.event_val false the light not reachable
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public class HueReachableEvent extends BooleanEvent implements HueLighEvent {

	public HueReachableEvent(Node source, PHLight light, Boolean value) {
		super(source, "light(" + light.getIdentifier() + ").reachable", value);
	}

}
