package org.rcplite.platform.spi;

import org.rcplite.platform.config.PlatformShellConfiguration;
import org.rcplite.platform.windows.ViewComponent;

public interface Shell {
    void launch();
    void addViewComponent(ViewComponent vc);
    void setConfiguration(PlatformShellConfiguration conf);
}

