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
