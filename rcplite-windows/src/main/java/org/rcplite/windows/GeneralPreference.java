package org.rcplite.windows;

import javax.swing.*;

import java.awt.*;

@PlatformPreference.Action(
        path = "General/Basic",
        id = GeneralPreference.class
)

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
