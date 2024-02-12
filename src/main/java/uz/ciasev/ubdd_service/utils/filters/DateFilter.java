package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class DateFilter<E> extends AbstractFilter<LocalDate, E> {

    public DateFilter(Function<LocalDate, Specification<E>> specificationSupplier) {
        super(specificationSupplier);
    }

    @Override
    protected LocalDate serialize(String value) {
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
