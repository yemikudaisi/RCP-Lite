package com.maplite.app;

import java.lang.annotation.*;

import com.maplite.core.utils.ReflectionUtils;
import junit.framework.TestCase;
import org.rcplite.windows.ViewComponent;

import static com.maplite.core.utils.VersionUtils.isJDK7OrLower;

public class ReflectionUtilsTest extends TestCase {

    @Retention (RetentionPolicy.RUNTIME)
    public @interface AnnotateThis {
        String name() default "";
    }

    public class AnnotateThisModifier implements AnnotateThis {
        String name;

        public AnnotateThisModifier(String name){ this.name = name; }
        @Override
        public String name() { return this.name;}

        @Override
        public Class<? extends Annotation> annotationType() {
            return AnnotateThisModifier.class;
        }
    }

    @AnnotateThis(name = "AnnotateThis")
    public class Beneficiary{
    }

    public void testAlterAnnotationOn() {
        final String MOD_NAME = "AnnotateThisModifier";

        System.err.println("JDK 7 ? " + isJDK7OrLower());
        // ViewComponent.Configuration configuration = ViewComponent.class.getAnnotation(ViewComponent.Configuration.class);
        AnnotateThis at = Beneficiary.class.getAnnotation(AnnotateThis.class);
        System.err.println("Hello there [" + at.name() + "]");
        AnnotateThisModifier altered = new AnnotateThisModifier(MOD_NAME);
        ReflectionUtils.alterAnnotationOn(Beneficiary.class, AnnotateThis.class, altered);
        at = Beneficiary.class.getAnnotation(AnnotateThis.class);
        System.err.println("After alteration...Hello there [" + at.name() + "]");
        assertEquals(at.name(), MOD_NAME);
    }
}