package org.rcplite.core.windows.controls;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.rcplite.api.windows.StatusBarItem;

/**
 * Base class for items in {@linkplain JMapStatusBar}.
 *
 * @author Michael Bedward
 * @since 8.0
 * @version $Id$
 */
public abstract class ShellStatusBarItem extends JPanel implements StatusBarItem {
    private static int ITEM_ID = 0;

    private final int id;
    private final boolean hasBorder;
    private final int minHeight;

    /**
     * Creates a new item with the given name. A border will be drawn for the item.
     *
     * @param name item name; or {@code null} for a default name
     */
    public ShellStatusBarItem(String name) {
        this(name, true);
    }

    /**
     * Creates a new item with the given name.
     *
     * @param name item name; or {@code null} for a default name
     * @param border whether to draw a border around this item
     */
    public ShellStatusBarItem(String name, boolean border) {
        this.id = ITEM_ID++;

        if (name == null || name.trim().length() == 0) {
            setName("Item_" + id);
        } else {
            setName(name);
        }

        this.hasBorder = border;
        if (hasBorder) {
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }

        setOpaque(false);
        minHeight = getHeight();
    }

    @Override
    public String getName() {
    	return super.getName();
    }
    
    @Override
    public Rectangle getBounds() {
    	return this.getBounds();
    }
    
    /**
     * Gets the unique integer ID assigned to this item.
     *
     * @return the item ID
     */
    @Override
	public int getID() {
        return id;
    }

    /**
     * Gets the minimum height of this item.
     *
     * @return minimum height
     */
    @Override
	public int getMinimumHeight() {
        return minHeight;
    }
    
    /**
     * For items that display numeric values, sets the number of digits to show to the right of the
     * decimal place. This base implementation does nothing.
     *
     * @param numDecimals number of digits after decimal place
     */
    public void setNumDecimals(int numDecimals) {}
    
    public JComponent getComponent(){
    	return this;
    }
}
