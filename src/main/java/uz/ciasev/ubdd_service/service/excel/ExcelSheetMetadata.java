package uz.ciasev.ubdd_service.service.excel;

import lombok.Getter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Getter
public class ExcelSheetMetadata {

    private final String name;

    private final List<String> columnsHeader;

    private final List<Method> columnsMethods;

    public ExcelSheetMetadata(String name, List<String> columnsHeader, List<Method> columnsMethods) {
        this.name = name;
        this.columnsHeader = Optional.ofNullable(columnsHeader).orElseGet(List::of);
        this.columnsMethods = Optional.ofNullable(columnsMethods).orElseGet(List::of);
    }
}
