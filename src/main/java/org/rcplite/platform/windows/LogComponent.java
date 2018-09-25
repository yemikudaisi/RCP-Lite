package org.rcplite.platform.windows;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;
import org.rcplite.platform.events.EventHandler;
import org.rcplite.platform.events.LogEvent;
import org.rcplite.platform.logging.Log;
import org.rcplite.platform.spi.Logger;
import org.rcplite.platform.utils.ImageManager;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

@ViewComponent.Configuration(
		position = ComponentPosition.OUTPUT,
		openOnStart = true
)
public class LogComponent extends ViewComponent implements Logger {
	JTextPane logTextPane;
	JButton clearButton;

	public LogComponent() {
		setTitle("Log");
		setIcon(ImageManager.getImageIcon("/images/log-info.png"));
		getEventAggregator().subscribe(this);
		logTextPane = new JTextPane();
		setLayout(new MigLayout("fill"));
		initUIComponents();
	}

	public void initUIComponents(){
        JScrollPane jsp = new JScrollPane(logTextPane);
        clearButton = new JButton(FontIcon.of(Feather.FTH_TRASH, 16,new Color(0XAFB1B3)));
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

    public void log(Log l) {
        log(l.getType().name()+": "+l.getMessage());
    }

	@EventHandler
	public <T extends Event> void handle(LogEvent event) {
		log(event.getLog());
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
