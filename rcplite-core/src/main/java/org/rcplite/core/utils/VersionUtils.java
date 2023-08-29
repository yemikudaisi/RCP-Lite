package com.maplite.core.utils;

public class VersionUtils {

    private static final String ANNOTATIONS = "annotations";

    public static boolean isJDK7OrLower() {
        boolean jdk7OrLower = true;
        try {
            Class.class.getDeclaredField(ANNOTATIONS);
        } catch (NoSuchFieldException e) {
            //Willfully ignore all exceptions
            jdk7OrLower = false;
        }
        return jdk7OrLower;
    }
}
