package org.rcplite.platform.core;

import org.rcplite.platform.services.PlatformEventBroker;
import org.rcplite.platform.spi.EventBroker;

import com.google.inject.AbstractModule;

public class PlatformModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EventBroker.class).to(PlatformEventBroker.class);
		
	}
	
}
