package uz.ciasev.ubdd_service.service.settings;

import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsCreateRequestDTO;

import java.util.List;

public interface OrganAccountBuilder {

    List<OrganAccountSettingsCreateRequest> build(OrganAccountSettingsCreateRequestDTO requestDTO);

}
