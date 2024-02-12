package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.court.CourtCaseChancelleryDataRepository;
import uz.ciasev.ubdd_service.repository.court.CourtCaseFieldsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtCaseFieldsServiceImpl implements CourtCaseFieldsService {

    private final CourtCaseFieldsRepository courtCaseFieldsRepository;
    private final CourtCaseChancelleryDataRepository chancelleryDataRepository;

    @Override
    public CourtCaseChancelleryData saveChancelleryData(CourtCaseChancelleryDataRequest request) {
        CourtCaseChancelleryData chancelleryData = chancelleryDataRepository.findByCaseIdAndClaimId(request.getCaseId(), request.getClaimId())
                .map(d -> d.setData(request))
                .orElseGet(() -> new CourtCaseChancelleryData(request));

        return chancelleryDataRepository.save(chancelleryData);
    }

    @Override
    public Optional<CourtCaseChancelleryData> findChancelleryData(CourtCaseFields courtCaseFields) {
        return findChancelleryData(courtCaseFields.getCaseId(), courtCaseFields.getClaimId());
    }

    @Override
    public Optional<CourtCaseChancelleryData> findChancelleryData(Long caseId, Long climeId) {
        return chancelleryDataRepository.findByCaseIdAndClaimId(caseId, climeId);
    }

    @Override
    public Optional<CourtCaseChancelleryData> findChancelleryData(AdmCase admCase) {
        if (admCase.getClaimId() == null) {
            return Optional.empty();
        }

        return findChancelleryData(admCase.getId(), admCase.getClaimId());
    }

    @Override
    public CourtCaseFields save(CourtCaseFields courtCaseFields) {
        return courtCaseFieldsRepository.save(courtCaseFields);
    }

    @Override
    public void handleSend(AdmCase admCase, Long climeId) {
        findByCaseId(admCase.getId())
                .ifPresent(f -> {
                    f.setClaimId(climeId);
                    courtCaseFieldsRepository.save(f);
                });
    }

    @Override
    public Optional<CourtCaseFields> findByCaseId(Long caseId) {
        return courtCaseFieldsRepository.findByCaseId(caseId);
    }

    @Override
    public CourtCaseFields getByCaseId(Long caseId) {
        return findByCaseId(caseId)
                .orElseThrow(() -> new EntityByParamsNotFound(CourtCaseFields.class, "caseId", caseId));
    }
}
