package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.court.CourtMaterialTypeRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractAliasedDictionaryListService;

@Getter
@Service
@RequiredArgsConstructor
public class CourtMaterialTypeServiceImpl extends AbstractAliasedDictionaryListService<CourtMaterialType, CourtMaterialTypeAlias> implements CourtMaterialTypeService {

    private final String subPath = "court-material-types";

    private final Class<CourtMaterialType> entityClass = CourtMaterialType.class;
    private final CourtMaterialTypeRepository repository;

    @Override
    public Class<CourtMaterialTypeAlias> getAliasClass() {
        return CourtMaterialTypeAlias.class;
    }
}
