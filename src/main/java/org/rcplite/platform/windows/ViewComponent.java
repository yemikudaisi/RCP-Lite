package org.rcplite.platform.windows;

import org.rcplite.platform.events.EventAggregator;
import org.rcplite.platform.services.EventService;

import java.lang.annotation.*;

public class ViewComponent extends AbstractComponent {


    protected EventAggregator getEventAggregator() {
        return EventService.getInstance().getAggregator();
    }

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
