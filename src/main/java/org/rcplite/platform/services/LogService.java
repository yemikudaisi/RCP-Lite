package org.rcplite.platform.services;

import org.rcplite.platform.events.LogEvent;
import org.rcplite.platform.logging.Log;
import org.rcplite.platform.logging.LogType;
import org.rcplite.platform.spi.Logger;

import java.util.ServiceLoader;

public class LogService {
    static LogService instance;
    private ServiceLoader<Logger> loader;;

    private LogService() {
        loader = ServiceLoader.load(Logger.class);
    }

    /**
     * Retrieve the singleton static instance of  LogService.
     */
    public static LogService instance(){
        if(instance == null){
            instance = new LogService();
        }
        return instance;
    }

    /**
     * Retrieve definitions from the first provider
     * that contains the word.
     */
    public void log(String word) {
        EventService.getInstance().getAggregator().publish(new LogEvent(new Log(
                LogType.INFO,
                word
        )));
    }

}
