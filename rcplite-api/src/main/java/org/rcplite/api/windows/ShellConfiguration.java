package org.rcplite.api.windows;

public interface ShellConfiguration {

	ShellConfiguration setShowToolboxOnStartup(boolean value);

	ShellConfiguration setShowLogOnStartup(boolean value);

	boolean showToolboxOnStartup();

	boolean showLogOnStartup();

	boolean isMaximizeOnStartup();

	ShellConfiguration setMaximizeOnStartup(boolean maximizeOnStartup);

	String getTitle();

	ShellConfiguration setTitle(String title);

}