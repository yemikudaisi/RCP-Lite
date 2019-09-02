package org.rcplite.api.windows;

import javax.swing.Icon;

import net.infonode.docking.View;

public interface Component {
    String getTitle();
    Icon getIcon();
    View getView();
}
