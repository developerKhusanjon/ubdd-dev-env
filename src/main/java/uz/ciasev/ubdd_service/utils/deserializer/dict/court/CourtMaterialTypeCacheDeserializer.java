package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.service.dict.court.CourtMaterialTypeService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CourtMaterialTypeCacheDeserializer extends AbstractDictDeserializer<CourtMaterialType> {

    @Autowired
    public CourtMaterialTypeCacheDeserializer(CourtMaterialTypeService courtStatusDictionaryService) {
        super(CourtMaterialType.class, courtStatusDictionaryService);
    }
}

