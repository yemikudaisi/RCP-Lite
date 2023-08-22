package org.rapidj.api.events;

import com.google.common.eventbus.EventBus;

public interface EventBroker {
	
	EventBus getBus();

}
