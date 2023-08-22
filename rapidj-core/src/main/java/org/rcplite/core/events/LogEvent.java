package org.rapidj.core.events;

import org.rapidj.api.logging.Log;

public class LogEvent{
    Log log;

    public LogEvent(Log log){
        this.log = log;
    }

    public Log getLog() {
        return log;
    }
}
