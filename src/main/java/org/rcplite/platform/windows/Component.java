package org.rcplite.platform.windows;

import net.infonode.docking.View;

import javax.swing.*;

public interface Component {
    String getTitle();
    Icon getIcon();
    View getView();
}
