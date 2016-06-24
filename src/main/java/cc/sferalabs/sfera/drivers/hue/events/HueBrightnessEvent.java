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
