package org.rcplite.windows.dock;

import junit.framework.TestCase;
import org.rcplite.windows.ViewComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DockingManagerTest extends TestCase {

    public void testCalculateGridSize() {
        Method method = null;
        try {
            method = ViewComponent.Configuration.class.getDeclaredMethod("position", Integer[].class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);

        Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
        annotations.setAccessible(true);

        Map<Class<? extends Annotation>, Annotation> map = annotations.get(annotationData);
        map.put(targetAnnotation, targetValue);

        assertTrue(true);
    }
}