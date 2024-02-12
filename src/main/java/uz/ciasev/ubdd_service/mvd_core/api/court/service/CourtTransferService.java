package uz.ciasev.ubdd_service.mvd_core.api.court.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.CourtTransferError;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtCompensationPayerType;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.dict.court.*;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransGeographyRepository;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransferDictionaryRepository;
import uz.ciasev.ubdd_service.repository.dict.VictimTypeRepository;
import uz.ciasev.ubdd_service.repository.dict.court.*;
import uz.ciasev.ubdd_service.repository.dict.evidence.CurrencyRepository;
import uz.ciasev.ubdd_service.repository.dict.evidence.EvidenceCategoryRepository;
import uz.ciasev.ubdd_service.repository.dict.evidence.EvidenceResultRepository;
import uz.ciasev.ubdd_service.repository.dict.evidence.MeasureRepository;
import uz.ciasev.ubdd_service.repository.dict.resolution.PunishmentTypeRepository;
import uz.ciasev.ubdd_service.repository.dict.resolution.TerminationReasonRepository;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransGeographyService;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CourtTransferService {

    private final CourtStatusRepository courtStatusRepository;
    private final CourtMaterialTypeRepository materialTypeRepository;
    private final EvidenceResultRepository evidenceResultRepository;
    private final MeasureRepository measureRepository;
    private final CurrencyRepository currencyRepository;
    private final VictimTypeRepository victimTypeRepository;
    private final PunishmentTypeRepository punishmentTypeRepository;
    private final EvidenceCategoryRepository evidenceCategoryRepository;
    private final TerminationReasonRepository terminationReasonRepository;
    private final CourtFinalResultRepository courtFinalResultRepository;
    private final CourtReturnReasonRepository returnReasonRepository;
    private final CourtTransGeographyRepository courtGeographyRepository;
    private final CourtTransferDictionaryRepository courtTransferRepository;
    private final CourtMaterialRejectBaseRepository materialRejectBaseRepository;
    private final CourtTransGeographyService transGeographyService;


    public CourtTransfer transferCourt(Long value) {
        return transferEntity(
                courtTransferRepository::findByExternalId,
                CourtTransfer.class,
                value
        );
    }

    public CourtStatus transferStatus(Long value) {
        return transferEntity(
                courtStatusRepository::findById,
                CourtStatus.class,
                value
        );
    }

    public CourtMaterialType transferMaterialType(Long value) {
        return transferEntity(
                materialTypeRepository::findById,
                CourtMaterialType.class,
                value
        );
    }

    public EvidenceResult transferEvidenceResult(Long value) {
        return transferEntity(
                evidenceResultRepository::findById,
                EvidenceResult.class,
                value
        );
    }

    public EvidenceCategory transferEvidenceCategory(Long value) {
        return transferEntity(
                evidenceCategoryRepository::findById,
                EvidenceCategory.class,
                value
        );
    }

    public Measures transferMeasure(Long value) {
        return transferEntity(
                measureRepository::findById,
                Measures.class,
                value
        );
    }

    public Currency transferCurrency(Long value) {
        return transferEntity(
                currencyRepository::findById,
                Currency.class,
                value
        );
    }

    public CourtFinalResult transferFinalResult(Long value) {
        return transferEntity(
                courtFinalResultRepository::findById,
                CourtFinalResult.class,
                value
        );
    }

    public PunishmentType transferMainPunishmentType(Long value) {
        return transferEntity(
                punishmentTypeRepository::findById,
                PunishmentType.class,
                value
        );
    }

    public PunishmentType transferAdditionPunishmentType(Long value) {
        return transferEntity(
                punishmentTypeRepository::findByCourtAdditionalPunishmentId,
                PunishmentType.class,
                value
        );
    }

    public TerminationReason transferTerminationReason(Long value) {
        return transferEntity(
                terminationReasonRepository::findById,
                TerminationReason.class,
                value
        );
    }

    public CourtReturnReason transferReturnReason(Long value) {
        return transferEntity(
                returnReasonRepository::findById,
                CourtReturnReason.class,
                value
        );
    }

    public CourtTransDistrict transferDistrict(Long value) {
        return transGeographyService.getCourtDistrictByExternalId(value);

//        CourtTransGeography geography = transferEntity(
//                courtGeographyRepository::findByExternalDistrictId,
//                CourtTransGeography.class,
//                value
//        );
//
//        return geography.getDistrict();
    }

    public VictimType transferVictimType(Long value) {
        return transferEntity(
                victimTypeRepository::findById,
                VictimType.class,
                value
        );
    }

    public CourtMaterialRejectBase transferMaterialRejectBase(Long value) {
        return transferEntity(
                materialRejectBaseRepository::findById,
                CourtMaterialRejectBase.class,
                value
        );
    }


    public CourtCompensationPayerType transferPayerType(Long code) {
        for (CourtCompensationPayerType value : CourtCompensationPayerType.values()) {
            if (value.getCourtCode().equals(code))
                return value;
        }

        throw new CourtTransferError(CourtCompensationPayerType.class, code);
    }

    private <T> T transferEntity(Function<Long, Optional<T>> repository, Class<T> tClass, Long value) {
        return repository
                .apply(value)
                .orElseThrow(() -> new CourtTransferError(tClass, value));
    }
}
