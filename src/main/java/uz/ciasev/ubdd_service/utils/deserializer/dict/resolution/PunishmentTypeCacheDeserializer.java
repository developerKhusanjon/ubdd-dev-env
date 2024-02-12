package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class PunishmentTypeCacheDeserializer extends AbstractDictDeserializer<PunishmentType> {

    @Autowired
    public PunishmentTypeCacheDeserializer(DictionaryService<PunishmentType> punishmentTypeService) {
        super(PunishmentType.class, punishmentTypeService);
    }
}