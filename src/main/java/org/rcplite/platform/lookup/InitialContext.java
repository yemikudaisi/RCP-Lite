package org.rcplite.platform.lookup;

import org.rcplite.platform.windows.LogComponent;

public class InitialContext {

    public Object lookup(String jndiName){
        if(jndiName.equalsIgnoreCase("LogService")){
            return new LogComponent();
        }
        return null;
    }
}
