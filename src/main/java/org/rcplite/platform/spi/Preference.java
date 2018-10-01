package org.rcplite.platform.spi;

import javax.swing.JComponent;

/**
 * Allows editing of a set of related user preferences.
 *
 * <P>Implementations of this interface do not define a "stand-alone" GUI,
 * but rather a component (usually a <tt>JPanel</tt>) which can be used by the
 * caller in any way they want. Typically, a set of <tt>PreferencesEditor</tt>
 * objects are placed in a <tt>JTabbedPane</tt>, one per pane.
 */
public interface Preference {

    /**
     * Return a GUI component which allows the user to edit this set of related
     * preferences.
     */
    JComponent getViewComponent();

    /**
     * The name of the tab in which this <tt>PreferencesEditor</tt>
     * will be placed.
     */
    String getTitle();

    /**
     * The mnemonic to appear in the tab name.
     *
     * <P>Must match a letter appearing in {@link #getTitle}.
     * Use constants defined in <tt>KeyEvent</tt>, for example <tt>KeyEvent.VK_A</tt>.
     */
    int getMnemonic();

    /**
     * Store the related preferences as they are currently displayed, overwriting
     * all corresponding settings.
     */
    void savePreferences();

    /**
     * Reset the related preferences to their default values, but only as
     * presented in the GUI, without affecting stored preference values.
     *
     * <P>This method may not apply in all cases. For example, if the item
     * represents a config which has no meaningful default value (such as a mail server
     * name), the desired behavior may be to only allow a manual change. In such a
     * case, the implementation of this method must be a no-operation.
     */
    void matchGuiToDefaultPreferences();
}
