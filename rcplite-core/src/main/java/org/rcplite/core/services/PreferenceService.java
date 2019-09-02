package org.rcplite.core.services;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.rcplite.core.spi.Preference;

public class PreferenceService {
    static PreferenceService instance;
    private ServiceLoader<Preference> loader;

    private PreferenceService() {
        loader = ServiceLoader.load(Preference.class);
    }

    /**
     * Retrieve the singleton static instance of  PreferenceService.
     */
    public static PreferenceService getInstance(){
        if(instance == null){
            instance = new PreferenceService();
        }
        return instance;
    }

    public Iterator<Preference> getPreferences() {
        Iterator<Preference> preferenceIterators = null;
        try {
            preferenceIterators = loader.iterator();
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
        return preferenceIterators;
    }
}
