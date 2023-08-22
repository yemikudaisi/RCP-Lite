package org.rapidj.windows;

import org.rapidj.api.windows.Shell;
import org.rapidj.api.windows.ShellConfiguration;
import javax.swing.*;

@SuppressWarnings("serial")
public abstract class AbstractShell extends JFrame implements Shell {
    private ShellConfiguration configuration;
    
	public abstract void launch();

    @Override
    public void setConfiguration(ShellConfiguration conf) {
        this.configuration = conf;
    }
    
    public ShellConfiguration getConfiguration() {
    	return this.configuration;
    };
}
