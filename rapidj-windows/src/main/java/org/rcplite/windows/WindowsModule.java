package org.rapidj.windows;

import org.rapidj.api.plugins.PluginModule;
import org.rapidj.api.windows.*;
import org.rapidj.windows.controls.MessageStatusBarItem;
import org.rapidj.windows.controls.ShellStatusBar;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

import javax.swing.*;

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

		Multibinder<ToolBar> toolBarBinder = Multibinder.newSetBinder( binder(), ToolBar.class);
		toolBarBinder.addBinding().to(DefaultToolBar.class).in(Scopes.SINGLETON);
	}
}
