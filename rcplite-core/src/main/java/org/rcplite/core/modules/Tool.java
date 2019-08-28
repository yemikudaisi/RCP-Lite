package org.rcplite.core.modules;

import org.rcplite.core.windows.Perspective;

import javax.swing.*;

public interface Tool {
    String getTitle();
    Icon getIcon();
    Perspective getPerspective();
}
