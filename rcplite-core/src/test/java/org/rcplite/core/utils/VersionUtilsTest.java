package com.maplite.core.utils;

import junit.framework.TestCase;

import static com.maplite.core.utils.VersionUtils.isJDK7OrLower;

public class VersionUtilsTest extends TestCase {

    public void testIsJDK7OrLower() {
        assertEquals(false, isJDK7OrLower());
    }
}