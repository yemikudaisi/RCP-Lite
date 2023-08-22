package org.rapidj.core.events;

import org.rapidj.api.events.EventBroker;

import com.google.common.eventbus.EventBus;

public class PlatformEventBroker  implements EventBroker {
	private EventBus bus;
	
	public PlatformEventBroker() {
		this.bus = new EventBus();
	}

	public EventBus getBus() {
		return bus;
	}
}
