package org.rcplite.platform.services;

import org.rcplite.platform.spi.Logger;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
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
    public static LogService getInstance(){
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
        try {
            Iterator<Logger> loggers = loader.iterator();
            while (loggers.hasNext()) {
                Logger l = loggers.next();
                l.log(word);
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
    }

}
