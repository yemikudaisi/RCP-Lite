package org.rapidj.windows.controls;

/**
 * A listener to work with controls derived from {@code JValueTextField}.
 *
 * @see JValueField
 * @see ValueChangedEvent
 * @author Michael Bedward
 * @since 2.6.1
 * @version $Id$
 */
public interface ValueChangedListener {

    /**
     * Called by the control whose value has just changed
     *
     * @param ev the event
     */
    public void onValueChanged(ValueChangedEvent ev);
}