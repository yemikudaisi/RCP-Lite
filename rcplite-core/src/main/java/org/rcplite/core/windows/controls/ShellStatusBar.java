	/*
	 *    GeoTools - The Open Source Java GIS Toolkit
	 *    http://geotools.org
	 *
	 *    (C) 2011, Open Source Geospatial Foundation (OSGeo)
	 *
	 *    This library is free software; you can redistribute it and/or
	 *    modify it under the terms of the GNU Lesser General Public
	 *    License as published by the Free Software Foundation;
	 *    version 2.1 of the License.
	 *
	 *    This library is distributed in the hope that it will be useful,
	 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
	 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	 *    Lesser General Public License for more details.
	 */

package org.rcplite.core.windows.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.rcplite.api.windows.PopupMenuProvider;
import org.rcplite.api.windows.StatusBar;
import org.rcplite.api.windows.StatusBarItem;
import org.rcplite.core.utils.ImageManager;
import org.rcplite.core.windows.dialog.AbstractSimpleDialog;
import org.rcplite.core.windows.dialog.DialogUtils;

import com.google.inject.Inject;

import net.miginfocom.swing.MigLayout;

/**
 * A status bar that works with a map pane to display cursor coordinates and other data. The static
 * {@linkplain #createDefaultStatusBar} method can be used for the most common configuration.
 *
 * @author Michael Bedward
 * @since 8.0
 * @version $Id$
 *
 */
public class ShellStatusBar extends AbstractStatusBar {

    private static final String CONFIGURE_TOOL_TIP = "Configure status bar items";

    private static final String SET_DECIMALS_STRING = "Set decimal precision for status bar items";

    private static final String DECIMAL_DIALOG_TITLE = "Configure DecimalPrecision";

    private static final String DECIMAL_DIALOG_LABEL = "Enter decimalplaces";

    private static final int INSET = 0;

    // Package-private constants for use by StatusBarItem classes
    public static final Font DEFAULT_FONT = new Font("Courier", Font.PLAIN, 12);
    public static final int DEFAULT_NUM_DECIMAL_DIGITS = 2;

    private int numDecimalDigits = DEFAULT_NUM_DECIMAL_DIGITS;
    private JPopupMenu configMenu;
    Set<StatusBarItem> statusBarItems;
    private final List<ItemInfo> itemInfo;
    private int minItemHeight;
    Logger logger = Logger.getLogger(ShellStatusBar.class.getName());

    /*
     * Stores item references and state.
     */
    private static class ItemInfo {
        final StatusBarItem item;
        final boolean configurable;
        final int componentIndex;
        boolean showing;

        public ItemInfo(StatusBarItem item, boolean configurable, int componentIndex, boolean showing) {

            this.item = item;
            this.configurable = configurable;
            this.componentIndex = componentIndex;
            this.showing = showing;
        }
    }
    public void addItems() {
    	for(StatusBarItem item:this.statusBarItems) {
    		this.addItem(item);
    	}
    }

    /**
     * Creates a new status bar. Sets a {@code MigLayout} layout manager and adds the default config
     * menu status item.
     */
    @Inject public ShellStatusBar(Set<StatusBarItem> items) {
    	this.statusBarItems = items;
        
        this.itemInfo = new ArrayList<ItemInfo>();

        setLayout(new MigLayout("insets " + INSET));
        setBackground(new Color(224, 224, 224));
        setFont(DEFAULT_FONT);
        setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY),
		        new EmptyBorder(4, 4, 4, 4)));


        ImageIcon icon = ImageManager.getImageIcon("/images/icons8-settings-16.png");
        PopupMenuProvider menuProvider =
                new PopupMenuProvider() {
                    @Override
                    public JPopupMenu getMenu() {
                        if (configMenu == null) {
                            configMenu = createItemMenu();
                        }
                        return configMenu;
                    }
                };

        StatusBarItem item = new MenuStatusBarItem("", icon, CONFIGURE_TOOL_TIP, menuProvider);
        addItem(item, false, true);
        if (SwingUtilities.isEventDispatchThread()) {
        	addItems();
        } else {
            try {
                SwingUtilities.invokeAndWait(
                        new Runnable() {
                            @Override
                            public void run() {
                            	addItems();
                            }
                        });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Adds a new item to the status bar. The item will display a border and appear in the status
     * bar configuration menu. If the item is already present in the status bar it will not added
     * again and the method will return {@code false}.
     *
     * @param item the item to add
     * @return {@code true} if the item was added
     */
    public boolean addItem(StatusBarItem item) {
        return addItem(item, true, true);
    }

    /**
     * Adds a new item to the status bar. If the item is already present in the status bar it will
     * not added again and the method will return {@code false}.
     *
     * @param item the item to add
     * @param configurable whether the item should appear in the status bar configuration menu
     * @param showing whether the item should be shown initially
     * @return {@code true} if the item was added
     */
    public boolean addItem(StatusBarItem item, boolean configurable, boolean showing) {

        if (findItem(item) < 0) {
            ItemInfo info = new ItemInfo(item, configurable, getComponentCount(), showing);
            itemInfo.add(info);

            if (showing) {
                add(item.getComponent());
            }

            int h = item.getMinimumHeight();
            if (h > minItemHeight) {
                minItemHeight = h;
                setMinimumSize(new Dimension(-1, minItemHeight));
            }

            // Set the status bar config menu to null so that it will
            // be re-created when next requested.
            configMenu = null;

            return true;

        } else {
            logger.log(
                    Level.WARNING,
                    "Item label:{0} id:{1} is already in the status bar",
                    new Object[] {item.getName(), item.getID()});
            return false;
        }
    }

    /**
     * Gets the number of items in this status bar including the default configuration menu item.
     *
     * @return number of status bar items
     */
    public int getNumItems() {
        return itemInfo.size();
    }

    /**
     * Searches for the given item in the current set of status bar items. If found, the item's
     * position index is returned; otherwise -1.
     *
     * @param item the item to search for
     * @return position index or -1 if not found
     */
    public int findItem(StatusBarItem item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not be null");
        }

        for (int i = 0; i < itemInfo.size(); i++) {
            if (itemInfo.get(i).item == item) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Gets the item at the specified position index. Position 0 is always occupied by the status
     * bar's configuration menu item.
     *
     * @param index position index between 0 and {@link #getNumItems()} - 1
     * @return the item
     * @throws IndexOutOfBoundsException on invalid {@code index} value
     */
    public StatusBarItem getItem(int index) {
        if (index >= 0 && index < getNumItems()) {
            return itemInfo.get(index).item;
        }

        throw new IndexOutOfBoundsException("Invalid item index: " + index);
    }

    /**
     * Creates a popup menu to configure the status bar. The names of configurable items will appear
     * as checkbox menu items to set whether they are shown or hidden. An additional item allows a
     * custom number of decimal places to be set for numeric items.
     *
     * @return the new pop-up menu
     */
    private JPopupMenu createItemMenu() {
        JPopupMenu menu = new JPopupMenu();

        // Add menu items to toggle display of status bar elements
        for (final ItemInfo info : itemInfo) {
            if (info.configurable) {
                JMenuItem menuItem = new JCheckBoxMenuItem(info.item.getName(), info.showing);
                menuItem.addActionListener(
                        new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                info.showing = !info.showing;
                                Rectangle r = info.item.getBounds();
                                if (info.showing) {
                                    add(info.item.getComponent(), info.componentIndex);
                                } else {
                                    remove(info.item.getComponent());
                                }
                                revalidate();
                                repaint(r);
                            }
                        });
                menu.add(menuItem);
            }
        }

        menu.addSeparator();
        JMenuItem menuItem = new JMenuItem(SET_DECIMALS_STRING);
        menuItem.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setNumDecimals();
                    }
                });
        menu.add(menuItem);

        return menu;
    }

    private void setNumDecimals() {
        DecimalDigitsDialog dialog = new DecimalDigitsDialog(numDecimalDigits);
        DialogUtils.showCentred(dialog);
        int n = dialog.getNumDigits();

        if (n >= 0) {
            numDecimalDigits = n;
            for (ItemInfo info : itemInfo) {
                info.item.setNumDecimals(numDecimalDigits);
            }
        }
    }

    private static class DecimalDigitsDialog extends AbstractSimpleDialog {

        private JIntegerField digitsFld;
        private int numDigits;

        public DecimalDigitsDialog(int initialValue) {
            super(DECIMAL_DIALOG_TITLE);
            numDigits = initialValue;
            initComponents();
        }

        @Override
        public JPanel createControlPanel() {
            JPanel panel = new JPanel(new MigLayout());

            panel.add(new JLabel(DECIMAL_DIALOG_LABEL), "gap related");

            digitsFld = new JIntegerField(numDigits, false);
            panel.add(digitsFld, "w 40!");

            return panel;
        }

        public int getNumDigits() {
            return numDigits;
        }

        @Override
        public void onOK() {
            numDigits = digitsFld.getValue();
            closeDialog();
        }
    }
}


