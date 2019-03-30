package org.rcplite.platform.config;

public class PlatformShellConfiguration {

    private boolean showToolboxOnStartup = false;
    private boolean maximizeOnStartup = false;
    private String title = "RCP Lite";

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
