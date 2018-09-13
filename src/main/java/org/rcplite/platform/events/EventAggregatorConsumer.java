package org.rcplite.platform.events;


import org.rcplite.platform.services.EventService;

public abstract class EventAggregatorConsumer {

    public EventAggregator getEventAggregator(Event event){

        return EventService.getInstance().getAggregator();
    }

}
