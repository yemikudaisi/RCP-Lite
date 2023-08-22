package org.rapidj.windows;

import javax.swing.*;

import org.rapidj.api.windows.PreferencePage;

public abstract class AbstractPreference extends JComponent implements PreferencePage {
    String title = "";
    int mnemonic = 0;
    JComponent ui;

    @Override
    public JComponent getViewComponent() {
        return this;
    }


    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(int mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public void matchGuiToDefaultPreferences() {

    }
}
