package org.rcplite.windows.dock;

/**
 * Represent the size of the grid of a docking manager
 */
public class DockGridSize {
    /**
     * The number of rows in the grid
     */
    int rowSize;

    /**
     * The number of columns in the grid
     */
    int columnSize;

    public DockGridSize(){
        this(1,1);
    }

    /**
     * The class constructor for the dock grid size
     * @param rowSize Integer representing the row size to be assigned
     * @param columnSize Integer representing the column size to be assigned
     */
    public DockGridSize(int rowSize, int columnSize){
        this.rowSize = rowSize;
        this.columnSize = columnSize;
    }

    /**
     * Gets the row size
     * @return Integer representing the row size
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * Gets the column size
     * @return Integer representing the column size
     */
    public int getColumnSize() {
        return columnSize;
    }


}
