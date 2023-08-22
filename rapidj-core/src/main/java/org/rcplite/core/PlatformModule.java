package org.rapidj.core;

import org.rapidj.api.events.EventBroker;
import org.rapidj.api.plugins.PluginModule;
import org.rapidj.api.services.LogService;
import org.rapidj.api.windows.ShellConfiguration;
import org.rapidj.core.config.PlatformShellConfiguration;
import org.rapidj.core.events.PlatformEventBroker;
import org.rapidj.core.services.PlatformLogService;

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
