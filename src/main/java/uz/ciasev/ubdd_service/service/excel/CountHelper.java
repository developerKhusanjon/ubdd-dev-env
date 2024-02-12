package uz.ciasev.ubdd_service.service.excel;


public class CountHelper {

    int row = -1;
    short column = -1;

    public short nextColumn() {
        column++;
        return column;
    }

    public int nextRow() {
        row++;
        column = -1;
        return row;
    }
}
