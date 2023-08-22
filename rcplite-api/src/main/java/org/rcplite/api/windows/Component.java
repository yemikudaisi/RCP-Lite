package org.rcplite.api.windows;

import javax.swing.Icon;

import net.infonode.docking.View;

public interface Component {
    String getTitle();
    Icon getIcon();
    View getView();

    /**
     * Called when a component comes in to view.
     * This allows the component to transfer focus to a child component
     */
    void setFocus();
}
