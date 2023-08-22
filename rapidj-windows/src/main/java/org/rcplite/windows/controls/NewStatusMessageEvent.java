package org.rapidj.windows.controls;

import org.rapidj.windows.event.BaseStringBasedEvent;

public class NewStatusMessageEvent extends BaseStringBasedEvent{

	public NewStatusMessageEvent(String message) {
		super(message);
	}

}
