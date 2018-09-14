package org.rcplite.platform.windows;

import org.rcplite.platform.spi.ShellConfiguration;

public class PlatformShellConfiguration implements ShellConfiguration {

    private boolean showToolboxOnStartup = false;

    public PlatformShellConfiguration setShowToolboxOnStartup(boolean value){
        showToolboxOnStartup = value;
        return this;
    }

    public boolean showToolboxOnStartup() {
        return showToolboxOnStartup;
    }
}
