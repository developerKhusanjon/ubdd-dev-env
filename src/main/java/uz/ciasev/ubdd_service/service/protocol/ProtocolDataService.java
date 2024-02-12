package uz.ciasev.ubdd_service.service.protocol;

import java.util.Optional;

public interface ProtocolDataService<T> {

    T save(T t);
    void delete(Long id);
    Optional<T> findByProtocolId(Long id);
}
