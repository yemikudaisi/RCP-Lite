package org.rcplite.platform.services;

import org.rcplite.platform.core.PlatformModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class DependencyProvider {
	private static Injector injector = null;
	public static Injector getInjector() {
		if (injector == null)
			injector = Guice.createInjector(new PlatformModule());
		
		return injector;
	}
	
	public static void addModuleBinding(AbstractModule ...modules) {
		injector = injector.createChildInjector(modules);
	}
	
	{
		injector = Guice.createInjector(new PlatformModule());
	}
}
