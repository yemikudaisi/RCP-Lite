package org.rcplite.core;

import org.rcplite.api.events.EventBroker;
import org.rcplite.api.plugins.PluginModule;
import org.rcplite.api.services.LogService;
import org.rcplite.api.windows.Shell;
import org.rcplite.api.windows.ShellConfiguration;
import org.rcplite.api.windows.Component;
import org.rcplite.core.config.PlatformShellConfiguration;
import org.rcplite.core.events.PlatformEventBroker;
import org.rcplite.core.services.PlatformLogService;
import org.rcplite.core.windows.PlatformShell;
import org.rcplite.core.windows.LogComponent;


import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

public class PlatformModule extends AbstractModule implements PluginModule{

	@Override
	protected void configure() {
		Multibinder<Component> mbinder = Multibinder.newSetBinder( binder(), Component.class);
		mbinder.addBinding().to(LogComponent.class);
		bind(Shell.class).to(PlatformShell.class);
		bind(ShellConfiguration.class).to(PlatformShellConfiguration.class);
		bind(EventBroker.class).to(PlatformEventBroker.class).in(Scopes.SINGLETON);
		bind(LogService.class).to(PlatformLogService.class);		
	}
	
}
