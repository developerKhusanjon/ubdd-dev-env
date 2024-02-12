package uz.ciasev.ubdd_service.service.excel.cell;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

@Component
public class DoubleCellValueSetter implements CellValueSetter {

    private CellStyle style;

    @Override
    public void setValue(Cell cell, Object value) {
        cell.setCellValue((Double) value);
        cell.setCellStyle(style);
    }

    @Override
    public boolean canSetType(Class type) {
        return Double.class.equals(type);
    }

    @Override
    public void createCellStyle(Workbook workbook, CreationHelper createHelper) {
        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
    }
}
