package org.rcplite.windows;

import org.rcplite.windows.dock.DockGridLocation;

import java.lang.annotation.*;

public abstract class ViewComponent extends AbstractComponent {

    public ViewComponent.Configuration getConfiguration(){
        return (ViewComponent.Configuration) this.getClass()
                .getAnnotation(ViewComponent.Configuration.class);
    }

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Configuration{
        int[] position() default {1,1};
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
