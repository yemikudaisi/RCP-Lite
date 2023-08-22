package org.rapidj.api.windows;


public interface Shell {
    void launch();
    void addViewComponent(Component component);
    void setConfiguration(ShellConfiguration conf);
}

