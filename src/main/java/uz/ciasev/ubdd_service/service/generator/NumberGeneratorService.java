package uz.ciasev.ubdd_service.service.generator;

import uz.ciasev.ubdd_service.entity.EntityNameAlias;

public interface NumberGeneratorService {

    Long incrementAndGet(EntityNameAlias entityNameAlias, long year);
}
