package org.rcplite.platform.events;

import org.rcplite.platform.logging.Log;

public class LogEvent extends Event {
    Log log;

    public LogEvent(Log log){
        this.log = log;
    }

    public Log getLog() {
        return log;
    }
}
