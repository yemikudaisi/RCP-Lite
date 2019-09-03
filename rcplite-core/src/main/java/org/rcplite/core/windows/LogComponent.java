package org.rcplite.core.windows;

import net.miginfocom.swing.MigLayout;

import org.rcplite.api.events.EventBroker;
import org.rcplite.core.events.LogEvent;
import org.rcplite.core.spi.Logger;
import org.rcplite.core.utils.ImageManager;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
@ViewComponent.Configuration(
		position = ComponentPosition.BOTTOM,
		openOnStart = false
)
@ViewComponent.Action(
        category = ActionCategory.VIEW,
        id= LogComponent.class
)
@ViewComponent.Menu(
        path = "Window/Show View/Log"
)
public class LogComponent extends ViewComponent implements Logger {
	JTextPane logTextPane;
	JButton clearButton;
	EventBroker eventBroker;

	@Inject public LogComponent(EventBroker broker) {
		this.eventBroker = broker;
		setTitle("Log");
		setIcon(ImageManager.getImageIcon("/images/log-info.png"));
		eventBroker.getBus().register(this);
		logTextPane = new JTextPane();
		setLayout(new MigLayout("fill"));
		initUIComponents();
	}

	public void initUIComponents(){
        JScrollPane jsp = new JScrollPane(logTextPane);
        clearButton = new JButton("Clear");
        clearButton.setMargin(new Insets(1,1,1,1));
        //clearButton.setSize(new Dimension(14,14));
        clearButton.setToolTipText("Clear log");
        add(clearButton,"wrap");
        clearButton.addActionListener(e -> logTextPane.setText(""));
        add(jsp,"grow, push, span");
	}

	//@Override
	public void execute(Object o) {
		if(!(o instanceof String))
			throw new IllegalArgumentException("Log Service Argument must be of type string");
		append((String)o);
	}

	@Override
	public void log(String s) {
	    append(s+System.lineSeparator());
	}

    @Subscribe public void log(LogEvent logEvent) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        log(sdf.format(cal.getTime()) +" :\t"+logEvent.getLog().getType().name()+" : \t"+logEvent.getLog().getMessage());
    }

	void append(String msg){
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(0x00FF33));

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = logTextPane.getDocument().getLength();
		logTextPane.setCaretPosition(len);
		logTextPane.setCharacterAttributes(aset, false);
		logTextPane.replaceSelection(msg);
	}
}
