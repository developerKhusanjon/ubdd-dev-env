package uz.ciasev.ubdd_service.service.court.material;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.*;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.court.CourtMaterialFieldsRepository;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourtMaterialFieldsServiceImpl implements CourtMaterialFieldsService {

    protected final CourtMaterialFieldsRepository courtFieldsRepository;
    protected final AliasedDictionaryService<CourtStatus, CourtStatusAlias> courtStatusService;


    @Override
    @Transactional
    public CourtMaterialFields move(CourtMaterialFields movedFields, Long toClimeId, Region courtRegion, District courtDistrict) {
        movedFields.setIsActive(false);
        movedFields.setMovedToClaimId(toClimeId);
        courtFieldsRepository.save(movedFields);

        CourtMaterialFields courtFields = new CourtMaterialFields();
        courtFields.setMaterialOf(movedFields);
        courtFields.setIsActive(true);
        courtFields.setClaimId(toClimeId);
        courtFields.setRegion(courtRegion);
        courtFields.setDistrict(courtDistrict);
        courtFields.setCourtStatus(courtStatusService.getByAlias(CourtStatusAlias.REGISTERED_IN_COURT));
        courtFields.setRegistrationNumber(movedFields.getRegistrationNumber());
        courtFields.setRegistrationDate(movedFields.getRegistrationDate());
        courtFields.setMaterialType(movedFields.getMaterialType());
        return courtFieldsRepository.saveAndFlush(courtFields);
    }


    @Override
    @Transactional
    public CourtMaterialFields review(CourtMaterialFields reviewedCourtFields, Long toClimeId) {
        reviewedCourtFields.setIsActive(false);
        courtFieldsRepository.save(reviewedCourtFields);

        CourtMaterialFields courtFields = new CourtMaterialFields();
        courtFields.setMaterialOf(reviewedCourtFields);
        courtFields.setIsActive(true);
        courtFields.setClaimId(toClimeId);
        courtFields.setReviewFromClaimId(reviewedCourtFields.getClaimId());
        courtFields.setCourtStatus(courtStatusService.getByAlias(CourtStatusAlias.REGISTERED_IN_COURT));
        courtFields.setRegistrationNumber(reviewedCourtFields.getRegistrationNumber());
        courtFields.setRegistrationDate(reviewedCourtFields.getRegistrationDate());
        courtFields.setMaterialType(reviewedCourtFields.getMaterialType());
        return courtFieldsRepository.saveAndFlush(courtFields);
    }

    @Override
    public CourtMaterialFields returnMaterial(CourtMaterialFields courtFields, CourtReturnReason courtReturnReason) {
        courtFields.setCourtReturnReason(courtReturnReason);

        return courtFieldsRepository.saveAndFlush(courtFields);
    }

    @Override
    public CourtMaterialFields granted(CourtMaterialFields courtFields) {
        if (courtFields.getMaterialType().is(CourtMaterialGroupAlias.APPEAL_ON_DECISION)) {
            throw new LogicalException("New resolution required for grant material appeal on decision");
        }

        courtFields.setIsGranted(true);

        return courtFieldsRepository.saveAndFlush(courtFields);
    }

    @Override
    public CourtMaterialFields reject(CourtMaterialFields courtFields, CourtMaterialRejectBase rejectBase) {

        courtFields.setRejectBase(rejectBase);
        courtFields.setIsGranted(false);

        return courtFieldsRepository.saveAndFlush(courtFields);
    }

    @Override
    public CourtMaterialFields grantedWithNewResolution(CourtMaterialFields courtFields, Resolution resolution) {
        if (courtFields.getMaterialType().not(CourtMaterialGroupAlias.APPEAL_ON_DECISION)) {
            throw new LogicalException("New resolution allowed only for grant material appeal on decision");
        }

        courtFields.setResolution(resolution);
        courtFields.setIsGranted(true);

        return courtFieldsRepository.saveAndFlush(courtFields);
    }

    @Override
    public CourtMaterialFields getCurrent(CourtMaterial courtMaterial) {
        return getCurrentOpt(courtMaterial)
                .orElseThrow(() -> new EntityByParamsNotFound(CourtMaterialFields.class, "climeId", courtMaterial.getClaimId()));
    }

    @Override
    public Optional<CourtMaterialFields> getCurrentOpt(CourtMaterial courtMaterial) {
        return findByClaimId(courtMaterial.getClaimId());
    }

    @Override
    public CourtMaterialFields open(CourtMaterial courtMaterial, CourtMaterialFieldsRegistrationRequest registration) {

        CourtMaterialFields courtFields = new CourtMaterialFields();

//        courtFields.setClaimId(registration.getClaimId());
//        courtFields.setCourtStatus(registration.getStatus());
//        courtFields.setRegistrationNumber(registration.getRegNumber());
//        courtFields.setRegistrationDate(registration.getRegDate());
        courtFields.setClaimId(courtMaterial.getClaimId());
        courtFields.setCourtStatus(courtStatusService.getByAlias(CourtStatusAlias.REGISTERED_IN_COURT));
        courtFields.setRegistrationNumber(registration.getRegistrationNumber());
        courtFields.setRegistrationDate(registration.getRegistrationDate());
        courtFields.setMaterial(courtMaterial);
        courtFields.setMaterialType(registration.getMaterialType());

        return courtFieldsRepository.saveAndFlush(courtFields);
    }

    @Override
    public CourtMaterialFields update(CourtMaterialFields courtFields, CourtMaterialFieldsRequest registration) {
        courtFields.setMaterialType(registration.getMaterialType());
        courtFields.setCourtStatus(registration.getCourtStatus());
        courtFields.setRegion(registration.getRegion());
        courtFields.setDistrict(registration.getDistrict());
        courtFields.setInstance(registration.getInstance());
        courtFields.setJudgeInfo(registration.getJudgeInfo());
        courtFields.setHearingDate(registration.getHearingTime());
        courtFields.setCaseNumber(registration.getCaseNumber());
        courtFields.setIsProtest(registration.getIsProtest());
        courtFields.setIsVccUsed(registration.getIsVccUsed());

        return courtFieldsRepository.save(courtFields);
    }

    /**
     * Некоторые статусы не влияют на обрабатываемый нами статус суда, и не меняют его, надо сохранить, не затирая статус отвечающий на вопрос какое было решение.
     * @param courtFields
     * @param status
     * @return CourtMaterialFields
     */
    @Override
    public CourtMaterialFields updateInformationStatus(CourtMaterialFields courtFields, CourtStatus status) {
        courtFields.setInformationCourtStatus(status);
        return courtFieldsRepository.save(courtFields);
    }

    @Override
    public Optional<CourtMaterialFields> findByClaimId(Long claimId) {
        return courtFieldsRepository.findByClaimId(claimId);
    }
}
