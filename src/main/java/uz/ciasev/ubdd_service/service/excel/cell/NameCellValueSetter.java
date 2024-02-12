package uz.ciasev.ubdd_service.service.excel.cell;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Component
public class NameCellValueSetter implements CellValueSetter {

    private CellStyle style;

    @Override
    public void setValue(Cell cell, Object value) {
        if (value == null) {
            return;
        }

        cell.setCellValue(((MultiLanguage) value).getLat());
        cell.setCellStyle(style);
    }

    @Override
    public boolean canSetType(Class type) {
        return MultiLanguage.class.equals(type);
    }

    @Override
    public void createCellStyle(Workbook workbook, CreationHelper createHelper) {
        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
    }
}
