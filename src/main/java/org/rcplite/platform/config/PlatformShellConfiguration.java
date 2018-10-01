package org.rcplite.platform.config;

public class PlatformShellConfiguration {

    private boolean showToolboxOnStartup = false;
    private boolean maximizeOnStartup = false;

    public PlatformShellConfiguration setShowToolboxOnStartup(boolean value){
        showToolboxOnStartup = value;
        return this;
    }

    public boolean showToolboxOnStartup() {
        return showToolboxOnStartup;
    }

    public boolean isMaximizeOnStartup() {
        return maximizeOnStartup;
    }

    public PlatformShellConfiguration setMaximizeOnStartup(boolean maximizeOnStartup) {
        this.maximizeOnStartup = maximizeOnStartup;
        return this;
    }
}
