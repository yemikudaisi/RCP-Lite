package org.rcplite.platform.spi;

import com.google.common.eventbus.EventBus;

public interface EventBroker {
	
	EventBus getBus();

}
