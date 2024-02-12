package uz.ciasev.ubdd_service.service.trans;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface CreateTransEntityService<T, D> extends TransEntityService<T> {

    TypeReference<? extends D> getCreateRequestDTOClass();

    List<T> create(List<D> request);

    T create(D request);
}
