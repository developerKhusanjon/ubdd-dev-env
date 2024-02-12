package uz.ciasev.ubdd_service.service.validation;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.user.User;

public interface ProtocolUniquenessValidationService {

    void validate(User user,
                  Person person,
                  ProtocolRequestDTO protocol);
}
