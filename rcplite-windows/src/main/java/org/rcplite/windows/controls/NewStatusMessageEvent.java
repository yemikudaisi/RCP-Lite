package org.rcplite.windows.controls;

import org.rcplite.windows.event.BaseStringBasedEvent;

public class NewStatusMessageEvent extends BaseStringBasedEvent{

	public NewStatusMessageEvent(String message) {
		super(message);
	}

}
