package org.rcplite.windows.dock;

public class DockGridLocation {
    int row;
    int column;
    int rowSpan;
    int columnSpan;

    public DockGridLocation(){
        this(1,1);
    }

    public DockGridLocation(int row, int column){
        this(row, column, 1, 1);
    }
    public DockGridLocation(int row, int column, int columnSpan, int rowSpan){
        this.row = row;
        this.column = column;
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
    }
}
