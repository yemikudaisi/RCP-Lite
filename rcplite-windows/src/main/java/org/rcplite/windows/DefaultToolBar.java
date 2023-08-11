package org.rcplite.windows;


import org.rcplite.api.windows.ToolBar;

import javax.swing.*;

class DefaultToolBar implements ToolBar {

    @Override
    public JToolBar getBar() {
        return new JToolBar();
    }

    @Override
    public String getName() {
        return "Default";
    }
}
