package org.rcplite.core.services;

import org.rcplite.api.windows.Shell;
import org.rcplite.api.windows.ShellService;

import com.google.inject.Inject;

public class PlatformShellService implements ShellService {
    Shell shell;

    @Inject public PlatformShellService(Shell shell) {
        this.shell = shell;
    }

    /**
     * Retrieve definitions from the first provider
     * that contains the word.
     */
    public Shell getShell() {
        return this.shell;
    }

}
