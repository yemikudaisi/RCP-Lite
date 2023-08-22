package org.rcplite.windows;

import org.rcplite.api.windows.Shell;
import org.rcplite.api.windows.ShellConfiguration;
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

    protected ViewComponent.Configuration getComponentConfiguration(Class type){
        return (ViewComponent.Configuration) type
                .getAnnotation(ViewComponent.Configuration.class);
    }
}
