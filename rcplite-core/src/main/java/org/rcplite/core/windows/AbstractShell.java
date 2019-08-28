package org.rcplite.core.windows;

import org.rcplite.api.windows.Shell;
import org.rcplite.api.windows.ShellConfiguration;
import org.rcplite.core.config.PlatformShellConfiguration;

import javax.swing.*;

public abstract class AbstractShell extends JFrame implements Shell {
    private ShellConfiguration configuration;
    
	public abstract void launch();

    @Override
    public void setConfiguration(ShellConfiguration conf) {
	    if( conf instanceof  PlatformShellConfiguration)
	        this.configuration = conf;
    }
    
    public ShellConfiguration getConfiguration() {
    	return this.configuration;
    };
}
