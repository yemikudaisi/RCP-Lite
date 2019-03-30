package org.rcplite.platform.config;

public class PlatformShellConfiguration {

    private boolean showToolboxOnStartup = false;
    private boolean maximizeOnStartup = false;
    private String title;

    public PlatformShellConfiguration setShowToolboxOnStartup(boolean value){
        showToolboxOnStartup = value;
        return this;
    }

    public static PlatformShellConfiguration getDefaultConfig(){
        PlatformShellConfiguration config = new PlatformShellConfiguration();
        config.setTitle("RCP Lite");
        return config;
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

    public String getTitle() {
        return title;
    }

    public PlatformShellConfiguration setTitle(String title) {
        this.title = title;
        return this;
    }
}
