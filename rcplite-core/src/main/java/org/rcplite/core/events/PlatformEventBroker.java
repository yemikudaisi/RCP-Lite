package org.rcplite.core.events;

import org.rcplite.api.events.EventBroker;

import com.google.common.eventbus.EventBus;

public class PlatformEventBroker  implements EventBroker {
	private EventBus bus;
	private static int id = 1;
	private int unique;
	
	public PlatformEventBroker() {
		this.bus = new EventBus();
		unique = id++;
		System.out.println("Broker:"+unique);
	}

	public EventBus getBus() {
		return bus;
	}
	
	public int getId() {
		return unique;
	}
}
