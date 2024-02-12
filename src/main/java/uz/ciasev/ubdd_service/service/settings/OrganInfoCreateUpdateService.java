package uz.ciasev.ubdd_service.service.settings;

import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganInfoCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganInfoUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;

public interface OrganInfoCreateUpdateService {

    OrganInfo create(OrganInfoCreateRequestDTO requestDTO);

    void update(Long id, OrganInfoUpdateRequestDTO requestDTO);
}
