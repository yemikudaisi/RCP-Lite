package org.rcplite.core.config;

import org.rcplite.api.windows.ShellConfiguration;

public class PlatformShellConfiguration implements ShellConfiguration {

    private boolean showToolboxOnStartup = false;
    private boolean showLogOnStartup = false;
    private boolean maximizeOnStartup = false;
    private float preferredExplorerWindowWidth = 0.2f;
    private float preferredPropertiesWindowWidth = 0.2f;
    private float preferredOutputWindowHeight = 0.25f;
    private String title;

    public PlatformShellConfiguration() {
        setTitle("RCP Lite");
    }
    @Override
	public ShellConfiguration setShowToolboxOnStartup(boolean value){
        showToolboxOnStartup = value;
        return this;
    }
    
    @Override
	public ShellConfiguration setShowLogOnStartup(boolean value){
        showLogOnStartup = value;
        return this;
    }
    
    @Override
	public boolean showToolboxOnStartup() {
        return showToolboxOnStartup;
    }
    
    @Override
	public boolean showLogOnStartup() {
        return showLogOnStartup;
    }

    @Override
	public boolean isMaximizeOnStartup() {
        return maximizeOnStartup;
    }

    @Override
	public ShellConfiguration setMaximizeOnStartup(boolean maximizeOnStartup) {
        this.maximizeOnStartup = maximizeOnStartup;
        return this;
    }

    @Override
	public String getTitle() {
        return title;
    }

    @Override
	public ShellConfiguration setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
	 * {@inheritDoc}
	 */
	@Override
	public ShellConfiguration setPreferredExplorerWindoWidth(float width) {
		// TODO: Guard against total of width exceeding one
		this.preferredExplorerWindowWidth = width;
		return this;
	}
	
	@Override
	public float getPreferredExplorerWindowWidth() {
		return this.preferredExplorerWindowWidth;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellConfiguration setPreferredPropertiesWindowWidth(float width) {
		// TODO: Guard against total of width exceeding one
		this.preferredPropertiesWindowWidth = width;
		return this;
	}
	
	@Override
	public float getPreferredPropertiesWindowWidth() {
		return this.preferredPropertiesWindowWidth;
	}
	
	@Override
	public ShellConfiguration setPreferredOutputWindowHeight(float height) {
		if(!(height > 1.0f)) {
			preferredOutputWindowHeight= height;
		}
		return this;
	}
	
	@Override
	public float getPreferredOutputWindowHeight() {
		return this.preferredOutputWindowHeight;
	}
}
