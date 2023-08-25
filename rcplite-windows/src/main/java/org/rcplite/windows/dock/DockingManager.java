package org.rcplite.windows.dock;

import net.infonode.docking.RootWindow;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import org.rcplite.windows.ViewComponent;

import java.util.Arrays;
import java.util.List;

/**
 * The Docking manager build docks from view components.
 * It is a convenient method for docking component while
 * abstracting the order and creation of required split windows.
 */
public class DockingManager {
    /**
     * Contains the components to be docked.
     */
    private List<ViewComponent> components;
    /**
     * The size of the grid components are to be docked in.
     */
    private DockGridSize gridSize;

    /**
     * Queues a view component in the docking manager for docking.
     * @param view
     */
    public void addView(final ViewComponent view) {
        this.components.add(view);
    }

    /**
     * Queues a collection view component in the docking manager for docking.
     * @param args
     */
    public void addViews(final ViewComponent... args) {
        this.components.addAll(Arrays.asList(args));
    }

    /**
     * Positions view component sin a root window.
     * @return the created root window containing all the docked view
     */
    public RootWindow createRootWindow() {
        RootWindow rootWindow;

        rootWindow = DockingUtil.createRootWindow(new ViewMap(), true);
        return rootWindow;
    }

    /**
     * Used to calculate the required grid size base on the position of
     * {@link ViewComponent ViewComponent}.
     * Uses {@link ViewComponent#getConfiguration() the view
     * components configuration}
     * @return The calculated dock grid size
     */
    public DockGridSize calculateGridSize() {
        if (this.components.isEmpty()) {
            return this.gridSize;
        }

        int largestRow = 1;
        int largestColumn = 1;
        for (ViewComponent component: this.components) {
            ViewComponent.Configuration conf = component.getConfiguration();
            if (conf != null) {
                largestRow = conf.position()[0];
                largestColumn = conf.position()[1];
            }
        }
        return new DockGridSize(largestRow, largestColumn);
    }
}
