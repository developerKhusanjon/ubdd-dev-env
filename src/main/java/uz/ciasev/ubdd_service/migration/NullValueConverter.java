package uz.ciasev.ubdd_service.migration;

import com.opencsv.bean.AbstractBeanField;

public class NullValueConverter extends AbstractBeanField<String, String> {
    @Override
    protected String convert(String value) {
        return value == null || value.trim().isEmpty() ? null : value;
    }
}
