package org.rcplite.platform.services;

import org.rcplite.platform.core.PlatformModule;
import org.rcplite.platform.events.EventAggregator;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class EventService {
	private static Injector injector = null;
	public static Injector getInjector() {
		if (injector == null)
			injector = Guice.createInjector(new PlatformModule());
		
		return injector;
	}
	
	public static void addModuleBinding(AbstractModule ...modules) {
		injector = injector.createChildInjector(modules);
	}
	
	{
		injector = Guice.createInjector(new PlatformModule());
	}
	
    static EventService instance;
    private ServiceLoader<EventAggregator> loader;;

    private EventService() {
        loader = ServiceLoader.load(EventAggregator.class);
    }

    /**
     * Retrieve the singleton static instance of  EventService.
     */
    public static EventService getInstance(){
        if(instance == null){
            instance = new EventService();
        }
        return instance;
    }

    /**
     * Retrieve EventAggregator from the first provider.
     */
    public EventAggregator getAggregator() {
        EventAggregator s = null;
        try {
            Iterator<EventAggregator> aggregators = loader.iterator();
            while (aggregators.hasNext()) {
                if(s != null)
                    return s;
                s = aggregators.next();
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
        return s;
    }
}
