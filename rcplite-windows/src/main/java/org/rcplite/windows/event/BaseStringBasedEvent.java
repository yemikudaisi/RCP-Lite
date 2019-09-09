package org.rcplite.windows.event;

import org.rcplite.api.plugins.StringBasedEvent;

public class BaseStringBasedEvent implements StringBasedEvent {
	private String string;
	
	public BaseStringBasedEvent(String message) {
		string = message;
	}
	
	public String getString() {
		return string;
	}
}
