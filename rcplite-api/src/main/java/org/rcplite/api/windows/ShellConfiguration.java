package org.rcplite.api.windows;

public interface ShellConfiguration {

	ShellConfiguration setShowToolboxOnStartup(boolean value);

	ShellConfiguration setShowLogOnStartup(boolean value);
	
	/**
	 * Sets the preferred fraction of width the properties window is to occupy
	 * in the shell frame
	 * 
	 * Note that the sum of the supplied explorer and properties width must
	 * not exceed 1.0
	 *  
	 * @param width a float ranging from 0.0 to 1 (in which is case it will
	 * take the entire width of the shell) defaults to 0.2
	 * @return instance of the shell configuration
	 * @see #setPreferredRightWindowWidth(float)
	 */
	ShellConfiguration setPreferredLeftWindoWidth(float width);
	
	float getPreferredExplorerWindowWidth();
	
	/**
	 * Sets the preferred fraction of width the properties window is to occupy
	 * in the shell frame
	 * 
	 * Note that the sum of the supplied explorer and properties width should
	 * not exceed 1.0
	 *  
	 * @param width a float ranging from 0.0 to 1 (in which is case it will
	 * take the entire width of the shell) defaults to 0.2
	 * @return instance of the shell configuration
	 * @see #setPreferredLeftWindoWidth(float)
	 */
	ShellConfiguration setPreferredRightWindowWidth(float width);
	
	float getPreferredPropertiesWindowWidth();
	
	/**
	 * Sets the preferred fraction of height the output window is to occupy
	 * in the shell frame
	 *  
	 * @param width a float ranging from 0.0 to 1 (in which is case it will
	 * take the entire height of the shell) defaults to 0.25
	 * @return instance of the shell configuration
	 * @see #setPreferredRightWindowWidth(float)
	 * @see #setPreferredLeftWindoWidth(float)
	 */
	ShellConfiguration setPreferredBottomWindowHeight(float height);
	
	float getPreferredOutputWindowHeight();

	boolean showToolboxOnStartup();

	boolean showLogOnStartup();

	boolean isMaximizeOnStartup();

	ShellConfiguration setMaximizeOnStartup(boolean maximizeOnStartup);

	String getTitle();

	ShellConfiguration setTitle(String title);

}