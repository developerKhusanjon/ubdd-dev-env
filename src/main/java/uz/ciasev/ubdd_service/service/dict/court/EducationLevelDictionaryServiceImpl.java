package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.EducationLevel;
import uz.ciasev.ubdd_service.repository.dict.court.EducationLevelRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class EducationLevelDictionaryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<EducationLevel>
        implements EducationLevelDictionaryService {

    private final String subPath = "education-level";

    private final EducationLevelRepository repository;
    private final Class<EducationLevel> entityClass = EducationLevel.class;
}
