package org.rcplite.windows;

import org.rcplite.api.plugins.PluginModule;
import org.rcplite.api.windows.Component;
import org.rcplite.api.windows.PreferencePage;
import org.rcplite.api.windows.Shell;
import org.rcplite.api.windows.StatusBar;
import org.rcplite.api.windows.StatusBarItem;
import org.rcplite.windows.controls.MessageStatusBarItem;
import org.rcplite.windows.controls.ShellStatusBar;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

public class WindowsModule extends AbstractModule implements PluginModule{

	@Override
	protected void configure() {
		Multibinder<Component> mbinder = Multibinder.newSetBinder( binder(), Component.class);
		mbinder.addBinding().to(LogComponent.class);
		
		Multibinder<StatusBarItem> shellStatusBarItemBinder = Multibinder.newSetBinder( binder(), StatusBarItem.class);
		shellStatusBarItemBinder.addBinding().to(MessageStatusBarItem.class).in(Scopes.SINGLETON);
		
		Multibinder<PreferencePage> prefBinder = Multibinder.newSetBinder( binder(), PreferencePage.class);
		prefBinder.addBinding().to(GeneralPreference.class);
		
		bind(StatusBar.class).to(ShellStatusBar.class).in(Scopes.SINGLETON);
		bind(Shell.class).to(PlatformShell.class);
	}	
}
