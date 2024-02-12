package uz.ciasev.ubdd_service.service.dict.person;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.dict.request.OccupationCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.OccupationUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.response.OccupationResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.dict.requests.OccupationCreateDTOI;
import uz.ciasev.ubdd_service.repository.dict.person.OccupationRepository;
import uz.ciasev.ubdd_service.service.UnknownValueService;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
@Service
@RequiredArgsConstructor
public class OccupationDictionaryServiceImpl extends AbstractDictionaryCRUDService<Occupation, OccupationCreateRequestDTO, OccupationUpdateRequestDTO>
        implements OccupationDictionaryService {


    private final String SubPath = "occupations";
    private final TypeReference<OccupationCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<OccupationUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>() {};

    private final Class<Occupation> entityClass = Occupation.class;
    private final OccupationRepository repository;

    private final Long unknownId = 999L;
    private final UnknownValueService unknownValueService;

    @Override
    public OccupationResponseDTO buildResponseDTO(Occupation entity) {
        return new OccupationResponseDTO(entity);
    }

    @Override
    @Transactional
    public Occupation createNewWithId(Long id) {
        Occupation newOccupation = new Occupation();
        newOccupation.construct(new OccupationCreateDTOI() {
            @Override
            public Long getId() {
                return id;
            }

            @Override
            public MultiLanguage getName() {
                return unknownValueService.getDictName();
            }

            @Override
            public String getCode() {
                return String.valueOf(id);
            }

            @Override
            public Boolean getIsWorker() {
                return false;
            }
        });

        newOccupation.close();
        repository.save(newOccupation);
        return newOccupation;
    }
}
