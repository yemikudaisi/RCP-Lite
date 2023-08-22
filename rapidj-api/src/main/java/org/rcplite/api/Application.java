package org.rapidj.api;

import org.rapidj.api.windows.ShellConfiguration;

import com.google.inject.Injector;

public abstract class Application {
	protected static Injector injector;
	protected ShellConfiguration config;
	
	public ShellConfiguration getShellConfiguration(){
		return this.config;
	}
	
	public static Injector getInjector() {
		return injector;
	}
}