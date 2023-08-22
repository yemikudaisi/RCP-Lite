package org.rapidj.windows.controls;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.rapidj.api.windows.StatusBar;
import org.rapidj.api.windows.StatusBarItem;

public abstract class AbstractStatusBar extends JPanel implements StatusBar{
	
	@Override
	public JComponent getComponent() {
		return this;
	}

}
