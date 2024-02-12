package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;

import java.util.Optional;

public interface CourtCaseFieldsService {

    CourtCaseChancelleryData saveChancelleryData(CourtCaseChancelleryDataRequest request);

    Optional<CourtCaseChancelleryData> findChancelleryData(CourtCaseFields courtCaseFields);

    Optional<CourtCaseChancelleryData> findChancelleryData(Long caseId, Long climeId);

    Optional<CourtCaseChancelleryData> findChancelleryData(AdmCase admCase);

    CourtCaseFields save(CourtCaseFields courtCaseFields);

    void handleSend(AdmCase admCase, Long climeId);

    Optional<CourtCaseFields> findByCaseId(Long caseId);

    CourtCaseFields getByCaseId(Long caseId);
}
