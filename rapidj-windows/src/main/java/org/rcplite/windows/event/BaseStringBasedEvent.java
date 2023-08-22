package org.rapidj.windows.event;

import org.rapidj.api.plugins.StringBasedEvent;

public class BaseStringBasedEvent implements StringBasedEvent {
	private String string;
	
	public BaseStringBasedEvent(String message) {
		string = message;
	}
	
	public String getString() {
		return string;
	}
}
