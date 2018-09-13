package org.rcplite.platform.modules;

import org.rcplite.platform.ui.Perspective;

import javax.swing.*;

public interface Tool {
    String getTitle();
    Icon getIcon();
    Perspective getPerspective();
}
