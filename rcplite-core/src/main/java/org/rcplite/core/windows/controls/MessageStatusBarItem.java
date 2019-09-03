package org.rcplite.core.windows.controls;

import javax.swing.JLabel;

import org.rcplite.api.events.EventBroker;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class MessageStatusBarItem extends ShellStatusBarItem {
    private static final String COMPONENT_NAME = "Status message";

    private static final String TOOL_TIP = "Status message";

    private final JLabel label;
    private EventBroker eventBroker;

    /**
     * Creates a new item to display the extent of the associated map pane.
     *
     * @param mapPane the map pane
     * @throws IllegalArgumentException if {@code mapPane} is {@code null}
     */
    @Inject public MessageStatusBarItem(EventBroker broker) {
        super(COMPONENT_NAME);
        eventBroker = broker;
        eventBroker.getBus().register(this);
        setToolTipText(TOOL_TIP);

        label = new JLabel("Ready");
        label.setFont(ShellStatusBar.DEFAULT_FONT);
        add(label);
    }
    
    @Subscribe public void displayNewMessage(NewStatusMessageEvent event) {
    	label.setText(event.getString());
    }

}
