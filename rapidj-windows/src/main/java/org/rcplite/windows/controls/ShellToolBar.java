package org.rapidj.windows.controls;

import org.rapidj.api.windows.ToolBar;

public abstract class ShellToolBar implements ToolBar {
	private String name;
	@Override
	public String getName() {
		return this.name;
	}

	protected void setName(String value) {
		this.name = value;
	}
}
