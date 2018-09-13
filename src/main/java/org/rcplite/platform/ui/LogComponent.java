package org.rcplite.platform.ui;

import net.miginfocom.swing.MigLayout;
import org.rcplite.platform.events.EventHandler;
import org.rcplite.platform.events.LogEvent;
import org.rcplite.platform.logging.Log;
import org.rcplite.platform.spi.Logger;
import org.rcplite.platform.windows.ComponentPosition;
import org.rcplite.platform.windows.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ViewComponent.Configuration(
		position = ComponentPosition.OUTPUT,
		openOnStart = true
)
public class LogComponent extends ViewComponent implements Logger {
	JTextArea logTextArea;
	JButton clearButton;

	public LogComponent() {
		setTitle("Log");
		getEventAggregator().subscribe(this);
		logTextArea = new JTextArea();
		setLayout(new MigLayout("fill"));
		initUIComponents();
	}

	public void initUIComponents(){
        JScrollPane jsp = new JScrollPane(logTextArea);
        clearButton = new JButton("Clear");
        add(clearButton,"wrap");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logTextArea.setText("");
            }
        });
        add(jsp,"grow, push, span");

	}

	//@Override
	public void execute(Object o) {
		if(!(o instanceof String))
			throw new IllegalArgumentException("Log Service Argument must be of type string");
		logTextArea.append((String)o);
	}

	@Override
	public void log(String s) {
	    logTextArea.append(s+System.lineSeparator());
	}

    public void log(Log l) {
        log(l.getType().name()+": "+l.getMessage());
    }

	@EventHandler
	public <T extends Event> void handle(LogEvent event) {
		log(event.getLog());
	}
}
