package org.rcplite.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.swing.SwingUtilities;

import org.rcplite.api.plugins.PluginModule;
import org.rcplite.api.windows.Shell;
import org.rcplite.api.windows.ShellConfiguration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Application {

	private static Injector injector;

	public static Injector getInjector() {
		return injector;
	}

	public Application() {
	}

	public void start() {
		List<Module> moduleList = new ArrayList<Module>();
		moduleList.add(new PlatformModule());
		
		ServiceLoader<PluginModule> extensions = ServiceLoader.load(PluginModule.class);
		for (PluginModule ext : extensions) {
			moduleList.add(ext);
		}
		injector = Guice.createInjector(moduleList);

		ShellConfiguration config = injector.getInstance(ShellConfiguration.class);
		config.setShowToolboxOnStartup(false).setTitle("RCP Lite").setMaximizeOnStartup(true);

		Shell shell = injector.getInstance(Shell.class);
		shell.setConfiguration(config);
		SwingUtilities.invokeLater(() -> shell.launch());
	}
}
