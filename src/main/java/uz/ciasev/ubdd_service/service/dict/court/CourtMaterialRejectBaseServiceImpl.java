package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialRejectBase;
import uz.ciasev.ubdd_service.repository.dict.court.CourtMaterialRejectBaseRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Getter
@Service
@RequiredArgsConstructor
public class CourtMaterialRejectBaseServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<CourtMaterialRejectBase> implements CourtMaterialRejectBaseService {

    private final String subPath = "court-material-reject-bases";

    private final Class<CourtMaterialRejectBase> entityClass = CourtMaterialRejectBase.class;
    private final CourtMaterialRejectBaseRepository repository;
}
