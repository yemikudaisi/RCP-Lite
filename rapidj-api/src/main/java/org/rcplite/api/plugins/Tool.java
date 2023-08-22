package org.rapidj.api.plugins;

import javax.swing.*;

import org.rapidj.api.windows.Perspective;

public interface Tool {
    String getTitle();
    Icon getIcon();
    Perspective getPerspective();
}
