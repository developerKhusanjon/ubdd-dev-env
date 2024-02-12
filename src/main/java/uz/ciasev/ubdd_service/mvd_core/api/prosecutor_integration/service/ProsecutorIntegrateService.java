package uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.service;



import uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.entity.ViolatorViolationResponse;

import java.util.List;

public interface ProsecutorIntegrateService {


    List<ViolatorViolationResponse> findViolations(
            Long pRequestID,
            String violatorPinpp,
            String violatorDocSeries,
            String violatorDocNumber
    );


}
