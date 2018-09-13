package org.rcplite.platform.events;

import java.io.Serializable;
import java.util.UUID;

public abstract class Event implements Serializable {
    protected final UUID correlationId;
    public Event(){
        this.correlationId = UUID.randomUUID();
    }

    public UUID getCorrelationId() {
        return correlationId;
    }
}
