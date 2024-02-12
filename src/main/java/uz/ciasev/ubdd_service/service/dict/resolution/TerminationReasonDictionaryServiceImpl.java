package uz.ciasev.ubdd_service.service.dict.resolution;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.TerminationReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.resolution.TerminationReasonResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.repository.dict.resolution.TerminationReasonRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class TerminationReasonDictionaryServiceImpl extends AbstractDictionaryCRUDService<TerminationReason, TerminationReasonRequestDTO, TerminationReasonRequestDTO>
        implements TerminationReasonDictionaryService {

    private final String subPath = "termination-reasons";
    private final TypeReference<TerminationReasonRequestDTO> updateRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<TerminationReasonRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    private final TerminationReasonRepository repository;
    private Set<Long> participateOfRepeatabilityCache = null;
    private final Class<TerminationReason> entityClass = TerminationReason.class;

    @Override
    public Set<Long> getParticipateOfRepeatabilityCached() {
        if (participateOfRepeatabilityCache == null) {
            participateOfRepeatabilityCache = repository.findAllByIsParticipateOfRepeatability(true)
                    .stream().map(TerminationReason::getId)
                    .collect(Collectors.toSet());
        }
        return participateOfRepeatabilityCache;
    }

    @Override
    public TerminationReasonResponseDTO buildResponseDTO(TerminationReason entity) {
        return new TerminationReasonResponseDTO(entity);
    }

}
