package uz.ciasev.ubdd_service.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.ArticleRequest;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.*;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimRequestDTO;
import uz.ciasev.ubdd_service.entity.AdmProtocol;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolDates;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.service.damage.DamageDetailService;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartDictionaryService;
import uz.ciasev.ubdd_service.service.dict.article.ArticleParticipantTypeDictionaryService;
import uz.ciasev.ubdd_service.service.participant.ParticipantDetailService;
import uz.ciasev.ubdd_service.service.settings.ArticleSettingsService;
import uz.ciasev.ubdd_service.service.victim.VictimDetailService;
import uz.ciasev.ubdd_service.specifications.ProtocolSpecifications;
import uz.ciasev.ubdd_service.utils.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantTypeAlias.Juridic;
import static uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantTypeAlias.Person;

@Service
@RequiredArgsConstructor
public class ProtocolValidationServiceImpl implements ProtocolValidationService {

    private final ArticleSettingsService organPartsSettingsService;
    private final ValidationService validationService;
    private final ArticleParticipantTypeDictionaryService articleParticipantTypeService;
    private final VictimDetailService victimDetailService;
    private final ParticipantDetailService participantDetailService;
    private final DamageDetailService damageDetailService;
    private final ProtocolRepository protocolRepository;
    private final ProtocolSpecifications protocolSpecifications;
    private final ArticlePartDictionaryService articlePartService;

    @Override
    public void validateViolatorByProtocol(AdmProtocol protocol, Person violator) {
        if (isActorExtremelyYoung(protocol, violator)) {
            throw new ValidationException(ErrorCode.VIOLATOR_EXTREMELY_YOUNG);
        }
    }

    @Override
    public void validateProtocolByUser(User user, ProtocolRequestDTO protocolDTO) {
        ValidationCollectingError errors = new ValidationCollectingError();

        errors.addIf(!organPartsSettingsService.checkAccessibleRegisterArticleByUser(user, protocolDTO.getArticlePart().getId()), ErrorCode.PROTOCOL_ARTICLE_NOT_AVAILABLE_FOR_REGISTRATION_BY_USER);

        errors.throwErrorIfNotEmpty();
    }

    @Override
    public void validateRegionDistrictByUser(User user, RegionDistrictRequest regionDistrictRequest) {

        if (validationService.checkRegionNotInUser(user, regionDistrictRequest.getRegion())) {
            throw new ValidationException(ErrorCode.PROTOCOL_REGION_NOT_AVAILABLE_FOR_USER);
        }

        if (validationService.checkDistrictNotInUser(user, regionDistrictRequest.getDistrict())) {
            throw new ValidationException(ErrorCode.PROTOCOL_DISTRICT_NOT_AVAILABLE_FOR_USER);
        }
    }

    @Override
    public void validateRaidRegionByUser(User user, RegionDistrictRequest regionDistrictRequest) {

        if (!validationService.checkRegionNotInUser(user, regionDistrictRequest.getRegion())) {
            throw new ValidationException(ErrorCode.RAID_PROTOCOL_REGION_NOT_AVAILABLE_FOR_USER);
        }
    }

    @Override
    public void validateQualificationByUser(User user, QualificationRequestDTO protocolDTO) {
        ValidationCollectingError errors = new ValidationCollectingError();

        errors.addIf(!organPartsSettingsService.checkAccessibleConsiderArticleByUser(user, protocolDTO.getArticlePart()), ErrorCode.NOT_CONSIDER_OF_ARTICLE_PART);

        errors.throwErrorIfNotEmpty();
    }

    @Override
    public void validateParticipantByProtocol(Protocol protocol,
                                              Person participant,
                                              ParticipantProtocolRequestDTO participantRequestDTO) {
        ValidationCollectingError errors = new ValidationCollectingError();

        Person violator = protocol.getViolatorDetail().getViolator().getPerson();

        errors.addIf(isActorNotAdult(protocol, participant), ErrorCode.PARTICIPANT_HAS_NOT_16_YEARS);
        errors.addIf(violator.getPinpp().equals(participant.getPinpp()), ErrorCode.VIOLATOR_AND_PARTICIPANTS_EQUAL);

        errors.throwErrorIfNotEmpty();

        validateParticipantUniqueness(protocol, participant, participantRequestDTO);
    }

    @Override
    public void validateVictimByProtocol(Protocol protocol, Person victim, VictimRequestDTO victimRequestDTO) {
        ValidationCollectingError errors = new ValidationCollectingError();

        Person violator = protocol.getViolatorDetail().getViolator().getPerson();

        errors.addIf(violator.getPinpp().equals(victim.getPinpp()), ErrorCode.VIOLATOR_AND_VICTIM_EQUAL);

        errors.throwErrorIfNotEmpty();

        validateVictimUniqueness(protocol, victim);
    }

    @Override
    public boolean validateJuridic(QualificationRequestDTO protocol) {
        return protocol.getIsJuridic() == null || protocol.getIsJuridic() != Objects.nonNull(protocol.getJuridic());
    }

    @Override
    public Optional<String> checkArticleWithParticipantType(ArticlePart articlePart, Boolean isJuridic) {
        if (Objects.isNull(articlePart) || Objects.isNull(isJuridic)) {
            return Optional.empty();
        }

        ArticleParticipantType articleParticipantType = articleParticipantTypeService.getById(articlePart.getArticleParticipantTypeId());

        if (articleParticipantType.is(Juridic) && !isJuridic) {
            return Optional.of(ErrorCode.THIS_ARTICLE_ONLY_FOR_JURIDIC);
        }

        if (articleParticipantType.is(Person) && isJuridic) {
            return Optional.of(ErrorCode.THIS_ARTICLE_ONLY_FOR_PERSON);
        }

        return Optional.empty();
    }

    @Override
    public boolean validateFirstDateLessThenSecond(LocalDateTime firstDate, LocalDateTime secondDate) {
        return (firstDate != null && !firstDate.isAfter(secondDate));
    }

    @Override
    public void validateVictimHasNoDamage(Long protocolId, Long victimId) {
        List<DamageDetail> rsl = damageDetailService.findByProtocolIdAndVictimId(protocolId, victimId);
        if (!rsl.isEmpty()) {
            throw new ValidationException(ErrorCode.VICTIM_HAS_DAMAGE_IN_PROTOCOL);
        }
    }

    @Override
    public void checkProtocolDates(ProtocolDates requestDTO) {
        if (requestDTO.getRegistrationTime() == null) {
            throw new ValidationException(ErrorCode.REGISTRATION_TIME_REQUIRED);
        }

        ValidationCollectingError error = new ValidationCollectingError();

        List<String> dateErrors = validateProtocolDates(requestDTO);
        for (String errorCode : dateErrors) {
            error.add(errorCode);
        }

        error.throwErrorIfNotEmpty();
    }

    @Override
    public void validatePaperProtocol(User inspector, PaperProtocolRequestDTO requestDTO) {

        checkBlankSeriesAndNumberByOrgan(inspector.getOrgan(), requestDTO.getPaperProps(), Optional.empty());

        ValidationCollectingError error = new ValidationCollectingError();

        if (requestDTO.getProtocol().getRegistrationTime() != null) {
            error.addIf(
                    requestDTO.getProtocol().getRegistrationTime().isAfter(LocalDateTime.now().plusMinutes(15)),
                    ErrorCode.PAPER_PROTOCOL_REGISTRATION_TIME_TOO_LARGE
            );
        }

        error.throwErrorIfNotEmpty();
    }

    @Override
    public void checkBlankSeriesAndNumberByOrgan(Organ organ, PaperPropsRequestDTO requestDTO, Optional<Protocol> editedProtocol) {

        Specification<Protocol> specification = protocolSpecifications.withOldNumber(requestDTO.getBlankNumber())
                .and(protocolSpecifications.withOldSeries(requestDTO.getBlankSeries()))
                .and(protocolSpecifications.withOrgan(organ));

        if (editedProtocol.isPresent()) {
            specification = specification.and(Specification.not(protocolSpecifications.withId(editedProtocol.get().getId())));
        }


        List<Long> protocols = protocolRepository.findAllId(specification);

        if (!protocols.isEmpty()) {
            throw new ValidationException(ErrorCode.PAPER_PROTOCOL_NUMBER_DUPLICATE);
        }
    }

    @Override
    public void validateMainArticle(User user, Protocol protocol, ProtocolArticle protocolArticle) {
        if (!protocolArticle.getProtocolId().equals(protocol.getId())) {
            throw new ValidationException(ErrorCode.PROTOCOL_AND_PROTOCOL_ARTICLE_NOT_CONSIST);
        }

        ArticlePart articlePart = articlePartService.getById(protocolArticle.getArticlePartId());

        ValidationCollectingError errors = new ValidationCollectingError();

        errors.add(checkArticleWithParticipantType(articlePart, protocol.getIsJuridic()));
        errors.addIf(
                !organPartsSettingsService.checkAccessibleConsiderArticleByUser(user, articlePart),
                ErrorCode.NOT_CONSIDER_OF_ARTICLE_PART
        );

        errors.throwErrorIfNotEmpty();

    }

    @Override
    public Optional<String> validateArticleViolationTypePresence(ArticleRequest requestDTO) {
        if (!validationService.checkViolationTypePresenceInArticleRequest(requestDTO)) return Optional.of(ErrorCode.ARTICLE_VIOLATION_TYPE_REQUIRED);

        return Optional.empty();
    }

    private LocalDate getViolationDateMinimumForDate(LocalDate toDate) {
        return toDate.minusYears(1).withDayOfYear(1);
    }

    private boolean isActorNotAdult(AdmProtocol protocol, Person person) {
        LocalDateTime violationTime = protocol.getViolationTime();
        LocalDate personBirthDate = person.getBirthDate();

//        LocalDate fullYears = violationTime.minus(16, ChronoUnit.YEARS);
//        return fullYears.isBefore(personBirthDate);

        return DateTimeUtils.getFullYearsBetween(personBirthDate, violationTime.toLocalDate()) < 16;
    }

    private boolean isActorExtremelyYoung(AdmProtocol protocol, Person person) {
        LocalDateTime violationTime = protocol.getViolationTime();
        LocalDate personBirthDate = person.getBirthDate();

        return DateTimeUtils.getFullYearsBetween(personBirthDate, violationTime.toLocalDate()) < 10;
    }

    private List<String> validateProtocolDates(ProtocolDates dto) {

        List<String> rsl = new LinkedList<>();

        if (!validateFirstDateLessThenSecond(dto.getViolationTime(), dto.getRegistrationTime())) {
            rsl.add(ErrorCode.REGISTRATION_TIME_MORE_THAN_VIOLATION_TIME); // IF VIOLATION TIME GREATER THEN REGISTRATION TIME
        }

        if (!validateViolationTimeMinimum(dto.getViolationTime())) {
            rsl.add(ErrorCode.VIOLATION_TIME_TOO_SMALL);
        }

//        if (validationService.checkNotCurrentYear(dto.getRegistrationTime())) {
//            rsl.add(ErrorCode.REGISTRATION_TIME_YEAR_DOES_NOT_EQUAL_CURRENT_YEAR);
//        }

        if (!dto.getRegistrationTime().isBefore(LocalDate.now().plusDays(1).atStartOfDay())) {
            rsl.add(ErrorCode.REGISTRATION_TIME_CANNOT_BE_IN_FUTURE);
        }

        return rsl;
    }

    private void validateVictimUniqueness(Protocol protocol, Person victim) {

        if (victimDetailService.existsByProtocolIdAndPersonId(protocol.getId(), victim.getId())) {
            throw new ValidationException(ErrorCode.VICTIM_ALREADY_IN_PROTOCOL);
        }
    }

    private void validateParticipantUniqueness(Protocol protocol,
                                               Person person,
                                               ParticipantProtocolRequestDTO participantProtocolRequestDTO) {

        boolean rsl = participantDetailService.existsByProtocolIdAndPersonId(
                protocol.getId(),
                person.getId(),
                participantProtocolRequestDTO.getParticipantType().getId()
        );

        if (rsl) {
            throw new ValidationException(ErrorCode.PARTICIPANT_ALREADY_IN_PROTOCOL);
        }
    }

    private boolean validateViolationTimeMinimum(LocalDateTime violationTime) {
        LocalDateTime violationTimeMin = getViolationDateMinimumForDate(LocalDate.now()).atStartOfDay();
        return (violationTime != null && !violationTime.isBefore(violationTimeMin));
    }
}