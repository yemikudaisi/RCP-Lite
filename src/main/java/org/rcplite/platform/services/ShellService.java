package org.rcplite.platform.services;

import org.rcplite.platform.spi.Shell;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class ShellService {
    static ShellService instance;
    private ServiceLoader<Shell> loader;;

    private ShellService() {
        loader = ServiceLoader.load(Shell.class);
    }

    /**
     * Retrieve the singleton static instance of  ShellService.
     */
    public static ShellService getInstance(){
        if(instance == null){
            instance = new ShellService();
        }
        return instance;
    }

    /**
     * Retrieve definitions from the first provider
     * that contains the word.
     */
    public Shell getShell() {
        Shell s = null;
        try {
            Iterator<Shell> shells = loader.iterator();
            while (shells.hasNext()) {
                if(s != null)
                    return s;
                s = shells.next();
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
        return s;
    }

}
