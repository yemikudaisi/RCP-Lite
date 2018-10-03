package org.rcplite.platform.windows;

import org.rcplite.platform.processing.ServiceProvider;
import org.rcplite.platform.spi.Preference;

import javax.swing.*;
import java.awt.*;

@PlatformPreference.Action(
        path = "General/Basic",
        id = GeneralPreference.class
)

@ServiceProvider(Preference.class)
public class GeneralPreference extends PlatformPreference {

    public GeneralPreference(){
        setLayout(new BorderLayout());
        setTitle("Basic");
        add(new JLabel("Basic Preference"), BorderLayout.CENTER);
    }

    @Override
    public void savePreferences() {

    }
}
