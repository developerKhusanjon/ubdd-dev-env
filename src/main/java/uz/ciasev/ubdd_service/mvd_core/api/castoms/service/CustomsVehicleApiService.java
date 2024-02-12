package uz.ciasev.ubdd_service.mvd_core.api.castoms.service;

import uz.ciasev.ubdd_service.mvd_core.api.castoms.dto.CustomsVehicleApiDTO;

public interface CustomsVehicleApiService {

    /**
     * @param eventId - Это идентификатор записи о вьезде машины в нашем сервисе. Это не id по каторой мы рабтает с сервисом таможни
     * @return
     */
    CustomsVehicleApiDTO getVehicleEventById(Long eventId);
}
