package org.rcplite.examples;

import org.rcplite.api.windows.ShellService;
import org.rcplite.core.RcpApplication;
import org.rcplite.core.config.PlatformShellConfiguration;
import javax.swing.SwingUtilities;

public class Quickstart {
  public static void main(String[] args) {

    RcpApplication app = new RcpApplication();
    app.getShellConfiguration()
            .setShowToolboxOnStartup(false)
            .setTitle("RCP Lite")
            .setMaximizeOnStartup(true)
            .setPreferredLeftWindoWidth(0.15f)
            .setPreferredRightWindowWidth(0.18f)
            .setPreferredBottomWindowHeight(0.2f);

    app.start();
  }
}
