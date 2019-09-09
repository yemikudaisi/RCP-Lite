package org.rcplite.windows.controls;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.rcplite.api.windows.StatusBar;
import org.rcplite.api.windows.StatusBarItem;

public abstract class AbstractStatusBar extends JPanel implements StatusBar{
	
	@Override
	public JComponent getComponent() {
		return this;
	}

}
