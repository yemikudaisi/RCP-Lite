package org.rcplite.windows.controls;


import java.awt.Component;

/**
 * An event published when the value of a control derived from {@code JValueField} changes.
 *
 * @see JValueField
 * @see ValueChangedListener
 * @author Michael Bedward
 * @since 2.6.1
 * @version $Id$
 */
public class ValueChangedEvent<T> {

    private Component source;
    private T newValue;

    /**
     * Create a value changed event
     *
     * @param source the control holding the value
     * @param newValue the updated value
     */
    public ValueChangedEvent(Component source, T newValue) {
        this.newValue = newValue;
        this.source = source;
    }

    /**
     * Get the control that invoked this event
     *
     * @return the invoking control
     */
    public Component getSource() {
        return source;
    }

    /**
     * Get the updated value
     *
     * @return the updated value
     */
    public T getValue() {
        return newValue;
    }
}