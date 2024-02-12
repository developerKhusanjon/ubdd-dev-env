package uz.ciasev.ubdd_service.mvd_core.api.texpass.service;

import uz.ciasev.ubdd_service.mvd_core.api.texpass.dto.UbddTechPassApiDTO;

public interface TechPassApiService {

    UbddTechPassApiDTO getTechPassById(Long vehicleId);
}
