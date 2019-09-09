package org.rcplite.windows;

import java.lang.annotation.*;

public abstract  class PlatformPreference extends AbstractPreference {

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Action{
        String path();
        Class id();
    }

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Menu{
        String path();
    }
}
