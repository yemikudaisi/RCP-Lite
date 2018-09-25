package org.rcplite.platform.utils;

import org.rcplite.platform.events.LogEvent;
import org.rcplite.platform.logging.Log;
import org.rcplite.platform.logging.LogType;
import org.rcplite.platform.services.EventService;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ErrorManager {

    public static String getStackTrace(Throwable throwable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        return result.toString();
    }

    public static void log(String message){
        EventService.getInstance().getAggregator().publish(new LogEvent(
                new Log(
                        LogType.ERROR,
                        "Message"
                )
        ));
        return;
    }

    public static void log(Exception exception){
        EventService.getInstance().getAggregator().publish(new LogEvent(
                new Log(
                        LogType.ERROR,
                        ErrorManager.getStackTrace(exception)
                )
        ));
        return;
    }

    public static void showDialog(Component parentComponent, String title, String message){
        JOptionPane.showMessageDialog(parentComponent ,message
                , title ,JOptionPane.ERROR_MESSAGE);
    }
}
