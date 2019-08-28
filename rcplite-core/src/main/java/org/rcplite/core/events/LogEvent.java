package org.rcplite.core.events;

import org.rcplite.api.logging.Log;

public class LogEvent{
    Log log;

    public LogEvent(Log log){
        this.log = log;
    }

    public Log getLog() {
        return log;
    }
}
