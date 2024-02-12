package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import uz.ciasev.ubdd_service.entity.protocol.Protocol;

public interface ProtocolDataDTO<T> {

    T build(Protocol protocol);
    T apply(T t, Protocol protocol);
}
