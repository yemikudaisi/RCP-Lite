package org.rcplite.platform.services;

import org.rcplite.platform.windows.Component;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class ComponentService {
    static ComponentService instance;
    private ServiceLoader<Component> loader;;

    private ComponentService() {
        loader = ServiceLoader.load(Component.class);
    }

    /**
     * Retrieve the singleton static instance of  ShellService.
     */
    public static ComponentService getInstance(){
        if(instance == null){
            instance = new ComponentService();
        }
        return instance;
    }

    /**
     * Retrieve definitions from the first provider
     * that contains the word.
     */
    public Iterator<Component> getComponents() {
        Iterator<Component> components = null;
        try {
            components = loader.iterator();
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
        return components;
    }

    public Component getComponent(Class clazz) {
        while(getComponents().hasNext()){
            Component next = getComponents().next();
            if(next.getClass() == clazz){
                return next;
            }
        }
        return null;
    }

}
