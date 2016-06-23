package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.events.Node;
import cc.sferalabs.sfera.events.NumberEvent;

/**
 * Event triggered when the brightness level of a light changes
 * 
 * @sfera.event_id light(&lt;id&gt;).brightness where &lt;id&gt; is the light ID
 * @sfera.event_val 1-254 brightness level of the light
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public class HueBrightnessEvent extends NumberEvent implements HueLighEvent {

	public HueBrightnessEvent(Node source, PHLight light, Integer value) {
		super(source, "light(" + light.getIdentifier() + ").brightness", value);
	}

}
