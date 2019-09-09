package org.rcplite.windows;

import java.lang.annotation.*;

public class ViewComponent extends AbstractComponent {

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Configuration{
        ComponentPosition position() default ComponentPosition.CENTER;
        boolean openOnStart() default false;
        boolean closable() default true;
        boolean minimizable() default true;
        boolean maximizable() default true;
        boolean restorable() default true;
        boolean dockable() default true;
        boolean dragable() default true;
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
