package org.rapidj.windows;


import org.rapidj.api.windows.ToolBar;

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
