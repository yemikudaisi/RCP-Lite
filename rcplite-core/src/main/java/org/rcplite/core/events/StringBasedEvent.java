package org.rcplite.core.events;

public abstract class StringBasedEvent {
	private String string;
	
	public StringBasedEvent(String message) {
		string = message;
	}
	
	public String getString() {
		return string;
	}
}
