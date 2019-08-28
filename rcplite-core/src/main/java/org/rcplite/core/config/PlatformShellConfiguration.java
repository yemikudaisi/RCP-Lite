package org.rcplite.core.config;

import org.rcplite.api.windows.ShellConfiguration;

public class PlatformShellConfiguration implements ShellConfiguration {

    private boolean showToolboxOnStartup = false;
    private boolean showLogOnStartup = false;
    private boolean maximizeOnStartup = false;
    private String title;

    @Override
	public ShellConfiguration setShowToolboxOnStartup(boolean value){
        showToolboxOnStartup = value;
        return this;
    }
    
    @Override
	public ShellConfiguration setShowLogOnStartup(boolean value){
        showLogOnStartup = value;
        return this;
    }

    public static ShellConfiguration getDefaultConfig(){
        ShellConfiguration config = new PlatformShellConfiguration();
        config.setTitle("RCP Lite");
        return config;
    }
    
    @Override
	public boolean showToolboxOnStartup() {
        return showToolboxOnStartup;
    }
    
    @Override
	public boolean showLogOnStartup() {
        return showLogOnStartup;
    }

    @Override
	public boolean isMaximizeOnStartup() {
        return maximizeOnStartup;
    }

    @Override
	public ShellConfiguration setMaximizeOnStartup(boolean maximizeOnStartup) {
        this.maximizeOnStartup = maximizeOnStartup;
        return this;
    }

    @Override
	public String getTitle() {
        return title;
    }

    @Override
	public ShellConfiguration setTitle(String title) {
        this.title = title;
        return this;
    }
}
