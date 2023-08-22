package org.rapidj.api.logging;

public class Log {

    LogTypes type;
    String message;

    public Log(LogTypes type, String message){
        this.type = type;
        this.message = message;
    }

    public LogTypes getType(){
        return this.type;
    }

    public String getMessage() {
        return message;
    }
}
