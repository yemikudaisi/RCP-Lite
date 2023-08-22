package org.rapidj.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.swing.SwingUtilities;

import org.rapidj.api.Application;
import org.rapidj.api.plugins.PluginModule;
import org.rapidj.api.windows.Shell;
import org.rapidj.api.windows.ShellConfiguration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class RcpApplication extends Application {

	public RcpApplication() {
		this(null);		
	}
	
	public RcpApplication(ShellConfiguration config) {
		List<Module> moduleList = new ArrayList<Module>();
		moduleList.add(new PlatformModule());
		
		ServiceLoader<PluginModule> extensions = ServiceLoader.load(PluginModule.class);
		for (PluginModule ext : extensions) {
			moduleList.add(ext);
		}
		injector = Guice.createInjector(moduleList);
		
		if(this.config == null) {
			this.config = injector.getInstance(ShellConfiguration.class);
			this.config.setShowToolboxOnStartup(false).setTitle("RCP Lite").setMaximizeOnStartup(true);
		}else {
			this.config = config;
		}
	}

	
	public void start() {
		Shell shell = injector.getInstance(Shell.class);
		shell.setConfiguration(config);
		SwingUtilities.invokeLater(() -> shell.launch());
	}
}
