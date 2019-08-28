package org.rcplite.core.windows;

import java.lang.annotation.*;

public abstract  class PlatformPreference extends AbstractPreference {

    @Override
    public abstract void savePreferences();

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
