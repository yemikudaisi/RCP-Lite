package org.rcplite.core.windows.controls;

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
