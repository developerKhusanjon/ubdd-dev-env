package uz.ciasev.ubdd_service.repository.prosecutor_integration;

import uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.entity.ViolatorViolation;
import java.util.List;


public interface ViolatorViolationRepository extends ReadOnlyRepository<ViolatorViolation, Long> {

    List<ViolatorViolation> findByViolatorPinppAndPunishmentIsNotNull(String pinpp);

    List<ViolatorViolation> findByDocumentSeriesAndDocumentNumberAndPunishmentIsNotNull(String documentSeries, String documentNumber);

}
