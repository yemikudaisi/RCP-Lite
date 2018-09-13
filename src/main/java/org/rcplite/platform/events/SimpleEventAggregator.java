package org.rcplite.platform.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SimpleEventAggregator implements EventAggregator{
    Map<Class<? extends Event>, List<Object>> eventHandlers;
    private static Logger LOG = Logger.getLogger(EventAggregator.class.getName());
    public SimpleEventAggregator(){
        eventHandlers = new HashMap<Class<? extends Event>, List<Object>>();
    }

    @Override
    public void subscribe(Object handler){
        Class clazz = handler.getClass();
        for(Method method : clazz.getMethods()){
            if(methodIsEventHandler(method)){
                Class[] parameters = method.getParameterTypes();
                if(parameters.length == 1){
                    subscribeClassToEvent(handler, parameters[0]);
                }
            }
        }

    }

    @Override
    public void publish(Event event){
        Class<? extends Event> type = event.getClass();
        List<Object> handlers = eventHandlers.get(event.getClass());
        if(handlers == null){
            LOG.info("No event handlers declared for event " + type.getName());
            return;
        }
        for(Object handler : handlers){
            for(Method method : handler.getClass().getMethods())
            {
                if(method.isAnnotationPresent(EventHandler.class) &&
                        method.getParameterTypes().length == 1  &&
                        method.getParameterTypes()[0].equals(event.getClass())){
                    try {
                        method.invoke(handler, event);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void unsubscribe(Object handler) {
        for(List<Object> handlers : eventHandlers.values()){
            handlers.remove(handler);
        }
    }


    private boolean methodIsEventHandler(Method method) {
        return method.isAnnotationPresent(EventHandler.class) &&
                method.getParameterTypes().length == 1 &&
                classIsEvent(method.getParameterTypes()[0]);
    }

    private boolean classIsEvent(Class clazz){
        return Event.class.isAssignableFrom(clazz) || clazz.isAssignableFrom(Event.class);
    }

    private void subscribeClassToEvent(Object handler, Class<? extends Event> eventToHandle) {
        if(eventHandlers.keySet().contains(eventToHandle)){
            eventHandlers.get(eventToHandle).add(handler);

        }else{
            List<Object> handlers = new ArrayList<Object>();
            handlers.add(handler);
            eventHandlers.put(eventToHandle, handlers);
        }
    }
}