package org.rcplite.core;

import org.rcplite.api.events.EventBroker;
import org.rcplite.api.plugins.PluginModule;
import org.rcplite.api.services.LogService;
import org.rcplite.api.windows.ShellConfiguration;
import org.rcplite.core.config.PlatformShellConfiguration;
import org.rcplite.core.events.PlatformEventBroker;
import org.rcplite.core.services.PlatformLogService;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class PlatformModule extends AbstractModule implements PluginModule{

	@Override
	protected void configure() {		
		bind(ShellConfiguration.class).to(PlatformShellConfiguration.class).in(Scopes.SINGLETON);
		bind(EventBroker.class).to(PlatformEventBroker.class).in(Scopes.SINGLETON);
		bind(LogService.class).to(PlatformLogService.class);	
	}
}
