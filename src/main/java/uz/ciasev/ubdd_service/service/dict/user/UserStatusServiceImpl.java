package uz.ciasev.ubdd_service.service.dict.user;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.UserStatusRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.UserStatusResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.user.UserStatus;
import uz.ciasev.ubdd_service.repository.dict.user.UserStatusRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class UserStatusServiceImpl extends AbstractDictionaryCRUDService<UserStatus, UserStatusRequestDTO, UserStatusRequestDTO>
        implements UserStatusService {
    private final String subPath = "user-statuses";
    private final TypeReference<UserStatusRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<UserStatusRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final UserStatusRepository repository;
    private final Class<UserStatus> entityClass = UserStatus.class;

    @Override
    public UserStatusResponseDTO buildResponseDTO(UserStatus entity) {
        return new UserStatusResponseDTO(entity);
    }

    @Override
    public UserStatusResponseDTO buildListResponseDTO(UserStatus entity) {return new UserStatusResponseDTO(entity);}

}
