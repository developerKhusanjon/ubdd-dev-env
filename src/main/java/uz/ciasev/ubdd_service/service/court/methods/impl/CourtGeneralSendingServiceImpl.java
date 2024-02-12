package uz.ciasev.ubdd_service.service.court.methods;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.*;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.projection.CourtTransferredArticleProjection;
import uz.ciasev.ubdd_service.entity.court.projection.CourtProtocolProjection;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.PrefixException;
import uz.ciasev.ubdd_service.exception.transfer.CourtArticleNotPresent;
import uz.ciasev.ubdd_service.exception.transfer.CourtGeographyNotPresent;
import uz.ciasev.ubdd_service.repository.court.CourtFirstMethodRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransferNationalityService;
import uz.ciasev.ubdd_service.service.court.files.CourtFileSendingService;
import uz.ciasev.ubdd_service.service.damage.DamageService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransGeographyService;
import uz.ciasev.ubdd_service.service.participant.ParticipantDetailService;
import uz.ciasev.ubdd_service.service.participant.ParticipantService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.victim.VictimDetailService;
import uz.ciasev.ubdd_service.service.victim.VictimService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;
import uz.ciasev.ubdd_service.utils.DateTimeUtils;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourtGeneralSendingServiceImpl implements CourtGeneralSendingService {

    private final DamageService damageService;
    private final CompensationService compensationService;
    private final DecisionService decisionService;
    private final ViolatorService violatorService;
    private final CourtFileSendingService fileSendingService;
    private final CourtTransferNationalityService courtNationalityService;
    private final CourtTransGeographyService courtGeographyService;
    private final AddressService addressService;
    private final VictimService victimService;
    private final VictimDetailService victimDetailService;
    private final ParticipantService participantService;
    private final ParticipantDetailService participantDetailService;
    private final CourtFirstMethodRepository firstMethodRepository;

    @Override
    public List<FirstCourtEvidenceRequestDTO> buildEvidence(Long admCaseId) {
        var evidencesTuple = firstMethodRepository.findCourtEvidenceInfoByAdmCaseId(admCaseId);
        var evidences = new ArrayList<FirstCourtEvidenceRequestDTO>();

        for (Tuple evidence : evidencesTuple) {
            var newEvidence = new FirstCourtEvidenceRequestDTO();
            newEvidence.setCaseId(evidence.get("case_id", Integer.class));
            newEvidence.setEvidenceId(evidence.get("evidence_id", Integer.class));
            newEvidence.setEvidenceCategory(evidence.get("evcat_id", Integer.class));
            newEvidence.setPersonDescription(evidence.get("fio", String.class));
            newEvidence.setEvidenceName(evidence.get("description", String.class));
            newEvidence.setEvidenceCountAndUnity(calcEvidenceQuantity(evidence));
            newEvidence.setMeasureId(evidence.get("measure_id", Integer.class));
            newEvidence.setCurrencyId(evidence.get("currency_id", Integer.class));
            newEvidence.setAmount(calcEvidenceCost(evidence));

            evidences.add(newEvidence);
        }
        return evidences;
    }

//    @Override
//    public List<FirstCourtClaimantRequestDTO> buildClaimants(Long admCaseId) {
//        var victimsTuple = admCaseService.findCourtVictimInfoByAdmCaseId(admCaseId);
//
//        var claimants = new ArrayList<FirstCourtClaimantRequestDTO>();
//        for (Tuple victim : victimsTuple) {
//            var claimant = new FirstCourtClaimantRequestDTO();
//            var victimId = victim.get("id", Integer.class);
//            var victimDetail = admCaseService.findCourtVictimDetailByAdmCaseAndVictimIds(admCaseId, victimId.longValue());
//            var protocolNumbers = admCaseService.findAllUniqueVictimProtocolNumbers(admCaseId, victimId.longValue());
//
//            claimant.setVictimId(victim.get("person_id", Integer.class));
//            claimant.setPinpp(calcPinpp(victim));
//            claimant.setClaimantLastName(victim.get("last_name_lat", String.class));
//            claimant.setClaimantFirstName(victim.get("first_name_lat", String.class));
//            claimant.setClaimantMiddleName(victim.get("second_name_lat", String.class));
//
//            claimant.setClaimantLastNameKir(victim.get("last_name_kir", String.class));
//            claimant.setClaimantFirstNameKir(victim.get("first_name_kir", String.class));
//            claimant.setClaimantMiddleNameKir(victim.get("second_name_kir", String.class));
//
//            claimant.setClaimantBirthdate(convertDateToLocalDate(victim.get("birth_date", Date.class)));
//            claimant.setClaimantAge(calcDefendantAge(victim.get("birth_date", Date.class)));
//            claimant.setClaimantCitizenship(victim.get("citizenship_type_id", Integer.class));
//            claimant.setClaimantGender(victim.get("gender_id", Integer.class));
//
//            var isForeign = victim.get("is_foreign", Boolean.class);
//
//            if (Boolean.TRUE.equals(isForeign)) {
//                claimant.setClaimantBirthCountry(victim.get("external_country_id", Integer.class));
//            } else {
//                claimant.setClaimantBirthCountry(victim.get("external_country_id", Integer.class));
//                claimant.setClaimantBirthRegion(victim.get("external_region_id", Integer.class));
//                claimant.setClaimantBirthDistrict(victim.get("external_district_id", Integer.class));
//            }
//
//            if (victim.get("citizenship_type_id", Integer.class) != null) {
//                long citizenshipTypeId = victim.get("citizenship_type_id", Integer.class).longValue();
//
//                CitizenshipType citizenshipType = citizenshipTypeService.getDTOById(citizenshipTypeId);
//                if (citizenshipType.is(CitizenshipTypeAlias.UZBEK) && victim.get("external_region_id", Integer.class) == null) {
//                    claimant.setClaimantBirthRegion(victimDetail.get("external_region_id", Integer.class));
//                }
//            }
//
//            claimant.setClaimantDocType(victimDetail.get("document_type_id", Integer.class));
//            claimant.setClaimantPassportSeries(victimDetail.get("document_series", String.class));
//            claimant.setClaimantPassportNumber(victimDetail.get("document_number", String.class));
//            claimant.setClaimantMobile(victimDetail.get("mobile", String.class));
//            claimant.setClaimantLivingDistrict(victimDetail.get("external_district_id", Integer.class));
//            claimant.setClaimantAddress(victimDetail.get("address", String.class));
//            claimant.setProtocolList(protocolNumbers);
//            claimants.add(claimant);
//        }
//        return claimants;
//    }

    public List<FirstCourtClaimantRequestDTO> buildClaimants(Long admCaseId) {
        return victimService.findByAdmCaseId(admCaseId).stream()
                .map(victim -> {
                    try {
                        return this.buildClaimant(victim);
                    } catch (ApplicationException e) {
                        throw new PrefixException(e, "VICTIM");
                    }
                })
                .collect(Collectors.toList());
    }

    public FirstCourtClaimantRequestDTO buildClaimant(Victim victim) {
        FirstCourtClaimantRequestDTO dto = new FirstCourtClaimantRequestDTO();

        Person person = victim.getPerson();
        List<Long> protocolNumbers = firstMethodRepository.findAllUniqueVictimProtocolNumbers(victim.getId());
        VictimDetail detail = victimDetailService.findByVictimIdAndProtocolId(victim.getId(), protocolNumbers.stream().findFirst().get());

        setActorData(dto, person, victim.getActualAddressId());
        dto.setClaimantMobile(victim.getMobile());

        dto.setClaimantDocType(detail.getPersonDocumentTypeId());
        dto.setClaimantPassportSeries(detail.getDocumentSeries());
        dto.setClaimantPassportNumber(detail.getDocumentNumber());

        dto.setProtocolList(protocolNumbers);

        return dto;
    }

    @Override
    public List<FirstCourtParticipantRequestDTO> buildParticipants(Long admCaseId) {
        return participantService.findAllByAdmCaseId(admCaseId).stream()
                .map(participant -> {
                    try {
                        return buildParticipant(participant);
                    } catch (ApplicationException e) {
                        throw new PrefixException(e, "PARTICIPANT");
                    }
                })
                .collect(Collectors.toList());

    }

    FirstCourtParticipantRequestDTO buildParticipant(Participant participant) {
        FirstCourtParticipantRequestDTO dto = new FirstCourtParticipantRequestDTO();

        List<Long> protocolNumbers = firstMethodRepository.findAllUniqueParticipantProtocolNumbers(participant.getId());
        ParticipantDetail detail = participantDetailService.findByParticipantIdAndProtocolId(participant.getId(), protocolNumbers.stream().findFirst().get());

        CourtAddress actualAddress = transferAddress(participant.getActualAddressId(), "ACTUAL_ADDRESS");
        setPersonData(dto, participant.getPerson(), actualAddress);

        dto.setParticipantType(participant.getParticipantTypeId());
        dto.setParticipantMobile(participant.getMobile());

        dto.setParticipantDocType(detail.getPersonDocumentTypeId());
        dto.setParticipantPassportSeries(detail.getDocumentSeries());
        dto.setParticipantPassportNumber(detail.getDocumentNumber());

        dto.setProtocolList(protocolNumbers);

        return dto;
    }

    @Override
    public List<FirstCourtDefendantRequestDTO> buildDefendants(AdmCase admCase, Resolution resolution) {
        List<Violator> violators = violatorService.findByAdmCaseId(admCase.getId());

        return violators.stream()
                .map(defendant -> {
                    try {
                        return buildDefendant(defendant, admCase, resolution);
                    } catch (ApplicationException e) {
                        throw new PrefixException(e, "VIOLATOR");
                    }
                })
                .collect(Collectors.toList());
    }

    private FirstCourtDefendantRequestDTO buildDefendant(Violator violator, AdmCase admCase, Resolution resolution) {
        FirstCourtDefendantRequestDTO defendant = new FirstCourtDefendantRequestDTO();
        var violatorId = violator.getId();
        CourtProtocolProjection protocolProjection = firstMethodRepository.findCourtProjectionByViolatorId(violatorId);

        if (resolution != null) {
            Optional<Decision> decisionOptional = decisionService
                    .findByResolutionAndViolatorIds(resolution.getId(), violatorId);

            Optional<PenaltyPunishment> penalty = decisionOptional
                    .map(Decision::getMainPunishment)
                    .map(Punishment::getPenalty);

            defendant.setAmountFixedPenalty(penalty.map(PenaltyPunishment::getAmount).orElse(null));
            defendant.setAmountPaidPenalty(penalty.map(PenaltyPunishment::getPaidAmount).orElse(null));


            Optional<Compensation> govCompensation = decisionOptional.map(d -> compensationService.findGovByDecision(d).orElse(null));
            govCompensation.ifPresent(compensation -> defendant.setAmountPaidDamage(compensation.getPaidAmount()));
        }


        Person person = violator.getPerson();
        setActorData(defendant, person, violator.getActualAddressId());
        defendant.setDefendantNationality(courtNationalityService.getExternalId(person.getNationality()));
        defendant.setDefendantMobile(violator.getMobile());
        defendant.setDefendantHealth(violator.getHealthStatusId());
        defendant.setDefendantEducation(violator.getEducationLevelId());
        defendant.setDefendantConvictedBefore(violator.getViolationRepeatabilityStatusId());
        defendant.setDefendantMaritalStatus(violator.getMaritalStatusId());
        defendant.setChildrenQty(violator.getChildrenAmount());

        Address postAddress = addressService.findById(violator.getPostAddressId());
        defendant.setDefendantAddress(postAddress.getAddress());


        if (protocolProjection != null) {
            defendant.setDefendantPassportSeries(protocolProjection.getViolatorDocumentSeries());
            defendant.setDefendantPassportNumber(protocolProjection.getViolatorDocumentNumber());
            defendant.setDefendantDocType(protocolProjection.getViolatorDocumentTypeId());
            defendant.setDefendantOccupation(protocolProjection.getOccupationId());
            defendant.setDefendantWorkplace(Optional.ofNullable(protocolProjection.getEmploymentPlace()).orElse("НЕ УКАЗАНО"));
            defendant.setDefendantPosition(Optional.ofNullable(protocolProjection.getEmploymentPosition()).orElse("НЕ УКАЗАНО"));
            defendant.setIsOfficial(protocolProjection.getIsJuridic());
            defendant.setInn(protocolProjection.getJuridicInn());
            defendant.setProtocolDistrictId(protocolProjection.getCourtDistrictId());
            defendant.setProtocolMtpId(protocolProjection.getMtpId());
            defendant.setOrganizationName(protocolProjection.getJuridicOrganizationName());
        }

        defendant.setArticles(getProtocolsArticles(violatorId));
        defendant.setConvictedBeforeArticles(getConvictedBeforeArticles(violatorId));
        defendant.setClaimCausedDamage(buildCausedDamage(admCase.getId(), violatorId));

        return defendant;
    }

//    private FirstCourtDefendantRequestDTO buildDefendant(CourtViolatorProjection violatorProjection, AdmCase admCase, Resolution resolution) {
//        FirstCourtDefendantRequestDTO defendant = new FirstCourtDefendantRequestDTO();
//        var violatorId = violatorProjection.getId();
//        CourtProtocolProjection protocolProjection = protocolRepository.findCourtProjectionByViolatorId(violatorId);
//
//        if (resolution != null) {
//            Optional<Decision> decisionOptional = decisionService
//                    .findByResolutionAndViolatorIds(resolution.getId(), violatorId);
//
//            Optional<PenaltyPunishment> penalty = decisionOptional
//                    .map(Decision::getMainPunishment)
//                    .map(Punishment::getPenalty);
//
//            defendant.setAmountFixedPenalty(penalty.map(PenaltyPunishment::getAmount).orElse(null));
//            defendant.setAmountPaidPenalty(penalty.map(PenaltyPunishment::getPaidAmount).orElse(null));
//
//
//            Optional<Compensation> govCompensation = decisionOptional.map(d -> compensationService.findGovByDecision(d).orElse(null));
//            govCompensation.ifPresent(compensation -> defendant.setAmountPaidDamage(compensation.getPaidAmount()));
//        }
//
//        defendant.setViolatorId(violatorProjection.getPersonId());
//        defendant.setPinpp(calcPinpp(violatorProjection));
//        defendant.setDefendantLastName(violatorProjection.getLastNameLat());
//        defendant.setDefendantFirstName(violatorProjection.getFirstNameLat());
//        defendant.setDefendantMiddleName(violatorProjection.getSecondNameLat());
//        defendant.setDefendantLastNameKir(violatorProjection.getLastNameKir());
//        defendant.setDefendantFirstNameKir(violatorProjection.getFirstNameKir());
//        defendant.setDefendantMiddleNameKir(violatorProjection.getSecondNameKir());
//        defendant.setDefendantNationality(violatorProjection.getCourtNationalityId());
//        defendant.setDefendantGender(violatorProjection.getGenderId());
//        defendant.setDefendantBirthdate(violatorProjection.getBirthDate());
//        defendant.setDefendantAge(calcDefendantAge(violatorProjection.getBirthDate()));
//        defendant.setDefendantCitizenship(violatorProjection.getCitizenshipTypeId());
//
//        defendant.setDefendantMobile(violatorProjection.getMobile());
//        defendant.setDefendantHealth(violatorProjection.getHealthStatusId());
//        defendant.setDefendantEducation(violatorProjection.getEducationLevelId());
//        defendant.setDefendantConvictedBefore(violatorProjection.getViolationRepeatabilityStatusId());
//        defendant.setDefendantMaritalStatus(violatorProjection.getMaritalStatusId());
//        defendant.setChildrenQty(violatorProjection.getChildrenAmount());
//        defendant.setDefendantRegion(violatorProjection.getActualAddressCourtDistrictId());
//        defendant.setDefendantCurrentAddress(violatorProjection.getActualAddressText());
//        defendant.setDefendantAddress(violatorProjection.getPostAddressText());
//
//        if (UZBEKISTAN.equals(violatorProjection.getBirthAddressCountryId())) {
//            defendant.setDefendantBirthCountry(violatorProjection.getBirthAddressCourtCountryId());
//            defendant.setDefendantBirthRegion(violatorProjection.getBirthAddressCourtRegionId());
//            defendant.setDefendantBirthDistrict(violatorProjection.getBirthAddressCourtDistrictId());
//        } else {
//            defendant.setDefendantBirthCountry(violatorProjection.getBirthAddressCourtCountryId());
//        }
//
//        if (CitizenshipTypeAlias.UZBEK.equals(violatorProjection.getCitizenshipTypeAlias()) && violatorProjection.getBirthAddressCourtRegionId() == null) {
//            defendant.setDefendantBirthRegion(violatorProjection.getActualAddressCourtRegionId());
//        }
//
//        if (protocolProjection != null) {
//            defendant.setDefendantPassportSeries(protocolProjection.getViolatorDocumentSeries());
//            defendant.setDefendantPassportNumber(protocolProjection.getViolatorDocumentNumber());
//            defendant.setDefendantDocType(protocolProjection.getViolatorDocumentTypeId());
//            defendant.setDefendantOccupation(protocolProjection.getOccupationId());
//            defendant.setDefendantWorkplace(Optional.ofNullable(protocolProjection.getEmploymentPlace()).orElse("НЕ УКАЗАНО"));
//            defendant.setDefendantPosition(Optional.ofNullable(protocolProjection.getEmploymentPosition()).orElse("НЕ УКАЗАНО"));
//            defendant.setIsOfficial(protocolProjection.getIsJuridic());
//            defendant.setInn(protocolProjection.getJuridicInn());
//            defendant.setProtocolDistrictId(protocolProjection.getCourtDistrictId());
//            defendant.setProtocolMtpId(protocolProjection.getMtpId());
//            defendant.setOrganizationName(protocolProjection.getJuridicOrganizationName());
//        }
//
//        var articles = buildConvictedBeforeArticles(violatorId);
//        defendant.setConvictedBeforeArticles(articles);
//
//        defendant.setArticles(buildArticles(violatorId, admCase.getId()));
//        defendant.setClaimCausedDamage(buildCausedDamage(admCase.getId(), violatorId));
//
//        return defendant;
//    }


    @Override
    public List<FirstCourtCauseDamageRequestDTO> buildCausedDamage(Long admCaseId, Long violatorId) {
        var damages = damageService.findByViolatorAndAdmCaseIds(admCaseId, violatorId);

        var collect = damages
                .stream()
                .map(FirstCourtFavorListRequestDTO::new)
                .collect(Collectors.groupingBy(FirstCourtFavorListRequestDTO::getGroup));

        var response = new ArrayList<FirstCourtCauseDamageRequestDTO>();
        for (Map.Entry<Long, List<FirstCourtFavorListRequestDTO>> entry : collect.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            var damage = new FirstCourtCauseDamageRequestDTO(key, value);
            response.add(damage);
        }
        return response;
    }

    @Override
    public List<FirstCourtFileRequestDTO> buildFiles(Long admCaseId) {
        return fileSendingService.buildFiles(admCaseId);
    }

    private void setPersonData(FirstCourtPersonRequestDTO dto, Person person, CourtAddress actualAddress) {
        dto.setId(person.getId());
        dto.setPinpp(calcPinpp(person));
        dto.setLastName(person.getLastNameLat());
        dto.setFirstName(person.getFirstNameLat());
        dto.setMiddleName(person.getSecondNameLat());
        dto.setLastNameKir(person.getLastNameKir());
        dto.setFirstNameKir(person.getFirstNameKir());
        dto.setMiddleNameKir(person.getSecondNameKir());
        dto.setGender(person.getGenderId());
        dto.setBirthdate(person.getBirthDate());
        dto.setCitizenship(person.getCitizenshipTypeId());
        dto.setAge(calcDefendantAge(person.getBirthDate()));

        dto.setDistrict(actualAddress.getDistrictId());
        dto.setCurrentAddress(actualAddress.getAddress());
    }

    private void setActorData(FirstCourtActorRequestDTO dto, Person person, Long actualAddressId) {
        CourtAddress actualAddress = transferAddress(actualAddressId, "ACTUAL_ADDRESS");
        CourtAddress birthAddress = transferAddress(person.getBirthAddressId(), "BIRTH_ADDRESS");

        setPersonData(dto, person, actualAddress);

        if (birthAddress.getIsUzbekistan()) {
            dto.setBirthCountry(birthAddress.getCountryId());
            dto.setBirthRegion(birthAddress.getRegionId());
            dto.setBirthDistrict(birthAddress.getDistrictId());
        } else {
            dto.setBirthCountry(birthAddress.getCountryId());
        }

        if (person.getCitizenshipType().is(CitizenshipTypeAlias.UZBEK) && birthAddress.getRegionId() == null) {
            dto.setBirthRegion(actualAddress.getRegionId());
        }
    }

    private List<FirstCourtArticleRequestDTO> getProtocolsArticles(Long violatorId) {
        return firstMethodRepository.findCourtArticlesByViolatorId(violatorId).stream()
                .map(article -> {
                    var dto = new FirstCourtArticleRequestDTO();
                    setArticleData(dto, article);
                    dto.setViolationTime(article.getViolationTime());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private void setArticleData(CourtArticleRequestDTO dto, CourtTransferredArticleProjection courtArticle) {

        if (courtArticle.getTransferId() == null) {
            throw new CourtArticleNotPresent(courtArticle.getInternalArticlePartId());
        }

        dto.setArticleId(courtArticle.getExternalArticleId());
        dto.setArticlePartId(courtArticle.getExternalArticlePartId());
        dto.setViolationId(courtArticle.getExternalArticleViolationTypeId());
    }

    public List<FirstCourtEarlyArticleRequestDTO> getConvictedBeforeArticles(Long violatorId) {
        return firstMethodRepository.findCourtConvictedBeforeArticlesByViolatorId(violatorId).stream()
                .map(article -> {
                    var dto = new FirstCourtEarlyArticleRequestDTO();
                    setArticleData(dto, article);
                    return dto;
                })
                .collect(Collectors.toList());
    }


    private String calcEvidenceQuantity(Tuple tuple) {
        BigDecimal quantity = tuple.get("quantity", BigDecimal.class);
        return quantity != null
                ? quantity.toString()
                : null;
    }

    private Long calcEvidenceCost(Tuple tuple) {
        BigDecimal cost = tuple.get("cost", BigDecimal.class);
        return (cost != null)
                ? cost.longValue()
                : null;
    }

    private Long calcDefendantAge(LocalDate localDate) {
        return (localDate != null)
                ? (long) DateTimeUtils.getFullYearsFor(localDate)
                : null;
    }

    private Long calcPinpp(Person tuple) {
        boolean isRealPinpp = tuple.isRealPinpp();
        String pinpp = tuple.getPinpp();

        return (isRealPinpp && pinpp != null) ? Long.valueOf(pinpp) : null;
    }

    private CourtAddress transferAddress(Long addressId, String addressType) {
        Address address = addressService.findById(addressId);
        CourtTransGeography geography;

        try {
            geography = courtGeographyService.getByInternal(address);
        } catch (CourtGeographyNotPresent e) {
            throw new PrefixException(e, addressType);
        }

        return new CourtAddress(
                geography.getExternalCountryId(),
                geography.getExternalRegionId(),
                geography.getExternalDistrictId(),
                address.getAddress(),
                address.isUzbekistan()
        );
    }
}
