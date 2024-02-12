package uz.ciasev.ubdd_service.utils.filters;

import org.springframework.data.jpa.domain.Specification;

public interface Filter<E> {

    Specification<E> getSpecification(String value);

}
