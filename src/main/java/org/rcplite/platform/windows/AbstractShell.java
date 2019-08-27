package org.rcplite.platform.windows;

import org.rcplite.platform.config.PlatformShellConfiguration;
import org.rcplite.platform.spi.Shell;

import javax.swing.*;

public abstract class AbstractShell extends JFrame implements Shell {
    private PlatformShellConfiguration configuration;
    
	public abstract void launch();

	public abstract void addViewComponent(ViewComponent vc);

    @Override
    public void setConfiguration(PlatformShellConfiguration conf) {
	    if( conf instanceof  PlatformShellConfiguration)
	        this.configuration = conf;
    }
    
    public PlatformShellConfiguration getConfiguration() {
    	return this.configuration;
    };
}
