/*-
 * +======================================================================+
 * Hue
 * ---
 * Copyright (C) 2016 Sfera Labs S.r.l.
 * ---
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * -======================================================================-
 */

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
