package org.rcplite.core.events;

import org.rcplite.api.events.EventBroker;

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
