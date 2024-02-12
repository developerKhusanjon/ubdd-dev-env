package uz.ciasev.ubdd_service.service.main.protocol;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

@Service
//@Profile("publicapi")
public class ProtocolCreateAdditionalValidationServicePublicApiImpl implements ProtocolCreateAdditionalValidationService {

    /**
     Protocol ID in external system
     **/
    @Override
    public void validateExternalId(ProtocolRequestDTO dto) {
        if (dto.getExternalId() == null) {
            throw new ValidationException(ErrorCode.EXTERNAL_ID_REQUIRED);
        }
    }
}
