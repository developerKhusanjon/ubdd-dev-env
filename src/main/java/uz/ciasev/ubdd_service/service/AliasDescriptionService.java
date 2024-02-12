package uz.ciasev.ubdd_service.service;

import uz.ciasev.ubdd_service.entity.AliasDescription;

import java.util.List;

public interface AliasDescriptionService {

    List<AliasDescription> findAllByAliasEnum(Class<? extends Enum<?>> aliasEnum);
}
