package org.rcplite.core.services;

import org.rcplite.api.events.EventBroker;

import com.google.common.eventbus.EventBus;

public class PlatformEventBroker  implements EventBroker {
	private EventBus bus;
	
	public PlatformEventBroker() {
		this.bus = new EventBus();
	} 
	@Override
	public EventBus getBus() {
		// TODO Auto-generated method stub
		return null;
	}
}
