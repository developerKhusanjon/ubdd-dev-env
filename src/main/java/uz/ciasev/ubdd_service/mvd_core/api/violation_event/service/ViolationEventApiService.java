package uz.ciasev.ubdd_service.mvd_core.api.violation_event.service;

import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;

public interface ViolationEventApiService {

    // Завязываемся на externalId так как customsEventId меняется при изменении статуса таможней (старая запись удаляетстся. новыя создаеться)
    ViolationEventApiDTO getById(Long id);
    void existById(Long id);
}
