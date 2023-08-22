package org.rapidj.api.windows;

import java.awt.Rectangle;

import javax.swing.JComponent;

public interface StatusBarItem {

	String getName();
	
	/**
	 * The physical bounds of the item's component in the user interface
	 * 
	 * @return
	 */
	Rectangle getBounds();
	
	/**
	 * Gets the unique integer ID assigned to this item.
	 *
	 * @return the item ID
	 */
	int getID();

	/**
	 * Gets the minimum height of this item.
	 *
	 * @return minimum height
	 */
	int getMinimumHeight();
	
	/**
	 * The visual component for the item
	 * @return
	 */
	JComponent getComponent();
	
	/**
	 * Precision point (number of digits to show to right of the decimal) 
	 * for Items that display numeric values
	 * 
	 * @param numDecimals
	 */
	public void setNumDecimals(int numDecimals);

}