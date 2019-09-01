package org.rcplite.api.windows;

public interface StatusBar {
	boolean addItem(StatusBarItem item);
	int getNumItems();
	int findItem(StatusBarItem item); 
	StatusBarItem getItem(int index);
	boolean addItem(StatusBarItem item, boolean configurable, boolean showing);
	javax.swing.JComponent getComponent();
}