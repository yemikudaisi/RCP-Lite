package org.rcplite.core.windows;

import java.lang.annotation.*;

public class ViewComponent extends AbstractComponent {

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Configuration{
        ComponentPosition position() default ComponentPosition.DOCUMENT;
        boolean openOnStart() default false;
    }

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Action{
        ActionCategory category();
        Class id();
    }

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Menu{
        String path();
    }

}
