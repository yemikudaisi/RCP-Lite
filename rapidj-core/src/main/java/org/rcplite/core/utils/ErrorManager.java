package org.rapidj.core.utils;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JOptionPane;

import org.rapidj.api.events.EventBroker;
import org.rapidj.api.logging.Log;
import org.rapidj.api.logging.LogTypes;
import org.rapidj.core.events.LogEvent;

import com.google.inject.Inject;

public class ErrorManager {
	EventBroker eventBroker;
	
    public static String getStackTrace(Throwable throwable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        return result.toString();
    }
    
    @Inject public ErrorManager(EventBroker broker) {
    	eventBroker = broker;
    }
    public void log(String message){
        eventBroker.getBus().post(new LogEvent(
                new Log(
                        LogTypes.ERROR,
                        "Message"
                )
        ));
        return;
    }

    public void log(Exception exception){
    	eventBroker.getBus().post(new LogEvent(
                new Log(
                        LogTypes.ERROR,
                        ErrorManager.getStackTrace(exception)
                )
        ));
        return;
    }

    public void showDialog(Component parentComponent, String title, String message){
        JOptionPane.showMessageDialog(parentComponent ,message
                , title ,JOptionPane.ERROR_MESSAGE);
    }
}
