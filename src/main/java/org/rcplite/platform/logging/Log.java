package org.rcplite.platform.logging;

public class Log {

    LogType type;
    String message;

    public Log(LogType type, String message){
        this.type = type;
        this.message = message;
    }

    public LogType getType(){
        return this.type;
    }

    public String getMessage() {
        return message;
    }
}
