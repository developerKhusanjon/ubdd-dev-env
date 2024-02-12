package uz.ciasev.ubdd_service.service.trans;

import com.fasterxml.jackson.core.type.TypeReference;

public interface UpdateTransEntityService<T, D> extends TransEntityService<T> {

    TypeReference<? extends D> getUpdateRequestDTOClass();

    T update(Long id, D request);
}
