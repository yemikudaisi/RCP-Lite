package org.rcplite.core.services;

import org.rcplite.api.events.EventBroker;
import org.rcplite.api.logging.Log;
import org.rcplite.api.logging.LogTypes;
import org.rcplite.api.services.LogService;
import org.rcplite.core.events.LogEvent;
import org.rcplite.core.spi.Logger;

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
