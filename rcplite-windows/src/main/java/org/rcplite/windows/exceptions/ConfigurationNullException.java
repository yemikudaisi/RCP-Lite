package org.rcplite.windows.exceptions;

public class ConfigurationNullException extends Exception {

    public ConfigurationNullException() {
        super("View component does not have annotated configuration.");
    }
}
