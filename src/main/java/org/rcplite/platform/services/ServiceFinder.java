package org.rcplite.platform.services;

//package org.rcplite.platform.services;

import org.rcplite.platform.spi.Shell;

import java.util.ServiceLoader;

public class ServiceFinder {
    static ServiceFinder instance = new ServiceFinder();
    private ServiceLoader<Shell> loader;

    //public static <Cl> void Find(ServiceType type){

    //}

}
