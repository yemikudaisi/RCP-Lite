package org.rcplite.platform.events;

import com.google.common.eventbus.EventBus;

public interface EventBroker {
	
	EventBus getBus();
	int getId();

}
