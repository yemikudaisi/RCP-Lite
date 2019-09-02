package org.rcplite.core.windows.controls;

import org.rcplite.core.events.StringBasedEvent;

public class NewMessageEvent extends StringBasedEvent{

	public NewMessageEvent(String message) {
		super(message);
	}

}
