package org.rcplite.core.services;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.rcplite.api.windows.PreferencePage;

public class PreferenceService {
    static PreferenceService instance;
    private ServiceLoader<PreferencePage> loader;

    private PreferenceService() {
        loader = ServiceLoader.load(PreferencePage.class);
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

    public Iterator<PreferencePage> getPreferences() {
        Iterator<PreferencePage> preferenceIterators = null;
        try {
            preferenceIterators = loader.iterator();
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
        return preferenceIterators;
    }
}
