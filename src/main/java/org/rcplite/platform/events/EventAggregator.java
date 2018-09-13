package org.rcplite.platform.events;

public interface EventAggregator {
    void subscribe(Object handler);

    void publish(Event event);

    void unsubscribe(Object handler);
}
