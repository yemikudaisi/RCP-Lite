package org.rcplite.core.windows.controls;

import org.rcplite.core.events.StringBasedEvent;

public class NewStatusMessageEvent extends StringBasedEvent{

	public NewStatusMessageEvent(String message) {
		super(message);
	}

}
