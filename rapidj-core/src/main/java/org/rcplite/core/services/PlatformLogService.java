package org.rapidj.core.services;

import org.rapidj.api.events.EventBroker;
import org.rapidj.api.logging.Log;
import org.rapidj.api.logging.LogTypes;
import org.rapidj.api.services.LogService;
import org.rapidj.api.services.Logger;
import org.rapidj.core.events.LogEvent;

import com.google.inject.Inject;

import java.util.ServiceLoader;

public class PlatformLogService implements LogService {
    static PlatformLogService instance;
    private ServiceLoader<Logger> loader;
    EventBroker eventBroker;
    
    @Inject
    private PlatformLogService(EventBroker eventBroker) {
    	this.eventBroker = eventBroker;
        loader = ServiceLoader.load(Logger.class);
    }


    /**
     * Retrieve definitions from the first provider
     * that contains the word.
     */
    public void log(String word) {
        eventBroker.getBus().post(new LogEvent(new Log(LogTypes.INFO,word)));
    }

	@Override
	public void log(Log log) {
		// TODO Auto-generated method stub
		
	}

}
