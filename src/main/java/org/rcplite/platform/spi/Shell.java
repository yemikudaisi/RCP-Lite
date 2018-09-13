package org.rcplite.platform.spi;

import org.rcplite.platform.windows.ViewComponent;

public interface Shell {
    void setVisible(boolean visible);
    void addViewComponent(ViewComponent vc);
}

