package cc.sferalabs.sfera.drivers.hue.events;

import com.philips.lighting.model.PHLight;

import cc.sferalabs.sfera.drivers.hue.Color;
import cc.sferalabs.sfera.events.BaseEvent;
import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when the color of a light changes.
 * <p>
 * See {@link Color} for details on the event value's class.
 * 
 * @sfera.event_id light(&lt;id&gt;).color where &lt;id&gt; is the light ID
 * @sfera.event_val color_obj object instance of the class Color representing
 *                  the current color of the light
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
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
