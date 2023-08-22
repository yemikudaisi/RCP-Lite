package org.rapidj.core.services;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.rapidj.api.plugins.Tool;

public class ToolsService {
    static ToolsService instance;
    private ServiceLoader<Tool> loader;;

    private ToolsService() {
        loader = ServiceLoader.load(Tool.class);
    }

    /**
     * Retrieve the singleton static instance of  ShellService.
     */
    public static ToolsService getInstance(){
        if(instance == null){
            instance = new ToolsService();
        }
        return instance;
    }

    /**
     * Retrieve definitions from the first provider
     * that contains the word.
     */
    public Iterator<Tool> getAll() {
        Iterator<Tool> tools = null;
        try {
            tools = loader.iterator();
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
        return tools;
    }

}
