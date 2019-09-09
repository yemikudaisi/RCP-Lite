package org.rcplite.api.plugins;

import javax.swing.*;

import org.rcplite.api.windows.Perspective;

public interface Tool {
    String getTitle();
    Icon getIcon();
    Perspective getPerspective();
}
