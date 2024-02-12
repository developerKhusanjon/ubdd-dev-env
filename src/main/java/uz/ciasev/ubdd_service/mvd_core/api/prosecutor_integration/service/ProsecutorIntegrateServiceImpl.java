package uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.entity.ViolatorViolation;
import uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.entity.ViolatorViolationResponse;
import uz.ciasev.ubdd_service.repository.prosecutor_integration.ViolatorViolationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProsecutorIntegrateServiceImpl implements ProsecutorIntegrateService {

    private final ViolatorViolationRepository repository;

    @Override
    public List<ViolatorViolationResponse> findViolations(
            Long pRequestID,
            String violatorPinpp,
            String violatorDocSeries,
            String violatorDocNumber
    ) {

        List<ViolatorViolation> violations;
        List<ViolatorViolationResponse> response = new ArrayList<>();

        if (violatorPinpp != null) {

            violations = repository.findByViolatorPinppAndPunishmentIsNotNull(violatorPinpp);

        } else if (violatorDocSeries != null && violatorDocNumber != null) {

            violations = repository.findByDocumentSeriesAndDocumentNumberAndPunishmentIsNotNull(violatorDocSeries, violatorDocNumber);

        } else {
            return response;
        }

        for(ViolatorViolation violation : violations) {
            ViolatorViolationResponse res = violation.toResponse();
            res.setPRequestId(pRequestID);
            res.setAnswerId(System.currentTimeMillis());
            response.add(res);
        }

        return response;

    }
}


