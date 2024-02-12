package uz.ciasev.ubdd_service.service.notification.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.*;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.dict.DistrictDictionaryService;
import uz.ciasev.ubdd_service.service.dict.RegionDictionaryService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.mib.MibValidationService;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.service.search.sms.SmsFullListResponseDTO;
import uz.ciasev.ubdd_service.service.search.sms.SmsSearchService;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignaturePasswordDTO;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SmsNotificationDTOServiceImpl implements SmsNotificationDTOService {

    private final ViolatorService violatorService;
    private final RegionDictionaryService regionDictionaryService;
    private final DistrictDictionaryService districtDictionaryService;
    private final SmsNotificationMessageService smsNotificationMessageService;
    private final SmsSearchService smsSearchService;
    private final InvoiceService invoiceService;

    @Override
    public SmsRequestDTO makeMibDTO(Decision decision, Organ organ) {
        Violator violator = violatorService.getById(decision.getViolatorId());
        Person person = violator.getPerson();
        Punishment mainPunishment = decision.getMainPunishment();
//        PenaltyPunishment penalty = mainPunishment.getPenalty();
//        long balance = penalty.getAmountForDate() - penalty.getPaidAmount();
        long balance = mainPunishment.getAmountForPayLeft();

        NotificationSmsTextMibRequestDTO textRequestDTO = NotificationSmsTextMibRequestDTO
                .builder()
                .fio(person.getShortFioLat())
                .birthDate(person.getBirthDate())
                .paymentAmount(balance)
                .mastByExecutedBeforeDate(decision.getMastByExecutedBeforeDate())
                .sendDateToMib(decision.getExecutionFromDate().plusDays(MibValidationService.getMibSendMinDays()))
                .build();

        String messageText = smsNotificationMessageService.buildMibMessage(textRequestDTO);
        return buildSmsRequestDTO(organ, decision, violator, NotificationTypeAlias.MIB_PRE_SEND, messageText);
    }

    @Override
    public List<SmsRequestDTO> makeCourtDTO(CourtCaseChancelleryData courtData, Organ organ) {

        List<SmsRequestDTO> rsl = new LinkedList<>();

        long caseId = courtData.getCaseId();
        List<Violator> violators = violatorService.findByAdmCaseId(caseId);

        for (var violator : violators) {
            var person = violator.getPerson();

            if (violator.isNotificationViaSms()) {
                NotificationSmsTextCourtRequestDTO notification = NotificationSmsTextCourtRequestDTO
                        .builder()
                        .fio(person.getShortFioLat())
                        .birthDate(person.getBirthDate())
                        .regNumber(courtData.getRegistrationNumber())
                        .courtRegionName(
                                Optional.ofNullable(courtData.getRegionId()).map(regionDictionaryService::getById).map(r -> r.getName().getLat()).orElse(null)
                        )
                        .courtDistrictName(
                                Optional.ofNullable(courtData.getDistrictId()).map(districtDictionaryService::getById).map(d -> d.getName().getLat()).orElse(null)
                        )
                        .build();

                String messageText = smsNotificationMessageService.buildCourtMessage(notification);
                SmsRequestDTO smsRequestDTO = buildSmsRequestDTO(organ, courtData, violator, NotificationTypeAlias.REGISTERED_IN_COURT, messageText);
                rsl.add(smsRequestDTO);
            }
        }
        return rsl;


    }

    @Override
    public SmsRequestDTO makePenaltyDecisionDTO(Decision decision,
                                                List<Compensation> compensations,
                                                Organ organ) {

        if (decision.getPenalty().isEmpty()) {
            throw new ImplementationException("makePenaltyDecisionDTO() only for decision with penalty");
        }

        Violator violator = decision.getViolator();
        Person person = violator.getPerson();
        Resolution resolution = decision.getResolution();
        Punishment mainPunishment = decision.getMainPunishment();
        PenaltyPunishment penalty = mainPunishment.getPenalty();
        Optional<Invoice> invoiceOpt = invoiceService.findByPenalty(penalty);

        NotificationSmsTextDecisionRequestDTO notification = NotificationSmsTextDecisionRequestDTO
                .builder()
                .fio(person.getShortFioLat())
                .birthDate(person.getBirthDate())
                .penaltyAmount(penalty.getAmount())
                .penaltyInvoiceNumber(invoiceOpt.map(Invoice::getInvoiceSerial).orElse(""))
                .penaltyPaymentDate(decision.getExecutionFromDate())
                .penaltyDiscount(penalty.getDiscount())
                .penaltyPaymentDateDiscount(penalty.getFirstDiscountForDate())
                .penaltyDiscountAmount(penalty.getFirstDiscountAmount())
                .organName(
                        Optional.ofNullable(resolution.getOrgan())
                                .map(Organ::getShortName)
                                .map(MultiLanguage::getLat)
                                .orElse("")
                )
                .regionName(
                        Optional.ofNullable(resolution.getRegion())
                                .map(Region::getName)
                                .map(MultiLanguage::getLat)
                                .orElse("")
                )
                .districtName(
                        Optional.ofNullable(resolution.getDistrict())
                                .map(District::getName)
                                .map(MultiLanguage::getLat)
                                .orElse("")
                )
                .build();

        // КОМПЕНСАЦИЯ В ПОЛЬЗУ ГОС-ВА
        Optional<Compensation> govCompens = compensations.stream()
                .filter(c -> c.getVictimType().is(VictimTypeAlias.GOVERNMENT))
                .findFirst();
        if (govCompens.isPresent()) {
            notification.setDamageAmount(govCompens.get().getAmount());
            notification.setDamageInvoiceNumber(govCompens.get().getInvoiceSerial());
        }

        // КОМПЕНСАЦИИ В ПОЛЬЗУ ПОСТРАДАВШИХ
        Long victimCompensAmount = compensations.stream()
                .filter(c -> c.getVictimType().not(VictimTypeAlias.GOVERNMENT))
                .map(Compensation::getAmount)
                .reduce(Long::sum).orElse(null);

        if (victimCompensAmount != null) {
            notification.setVictimDamageAmount(victimCompensAmount);
        }

        String messageText = smsNotificationMessageService.buildDecisionMessage(notification);

        return buildSmsRequestDTO(organ, decision, violator, NotificationTypeAlias.RESOLUTION_CREATE, messageText);
    }

    @Override
    public SmsRequestDTO makeProtocolDTO(Protocol protocol, Organ organ) {

        Violator violator = protocol.getViolatorDetail().getViolator();

        Person person = violator.getPerson();
        ArticlePart articlePart = protocol.getArticlePart();

        NotificationSmsTextProtocolRequestDTO rsl = NotificationSmsTextProtocolRequestDTO
                .builder()
                .fio(person.getShortFioLat())
                .birthDate(person.getBirthDate())
                .protocolSeries(protocol.getSeries())
                .protocolNumber(protocol.getNumber())
                .registrationDate(protocol.getRegistrationTime().toLocalDate())
                .articleName(articlePart.getShortName().getLat())
                .organName(
                        Optional.ofNullable(protocol.getOrgan().getShortName())
                                .map(MultiLanguage::getLat)
                                .orElse("")
                )
                .districtName(
                        Optional.ofNullable(protocol.getDistrict())
                                .map(District::getName)
                                .map(MultiLanguage::getLat)
                                .orElse("")
                )
                .build();

        String messageText = smsNotificationMessageService.buildProtocolMessage(rsl);
        return buildSmsRequestDTO(organ, protocol, violator, NotificationTypeAlias.PROTOCOL_CREATE, messageText);
    }

    @Override
    public SmsRequestDTO makeUserDigitalSignatureCertificatePasswordDTO(User user, DigitalSignaturePasswordDTO signaturePassword, Organ organ) {
        NotificationSmsTextDigitalSignaturePasswordRequestDTO rsl = NotificationSmsTextDigitalSignaturePasswordRequestDTO
                .builder()
                .serial(signaturePassword.getSerial())
                .password(signaturePassword.getPassword())
                .expiresOn(signaturePassword.getExpiresOn())
                .build();

        String messageText = smsNotificationMessageService.buildDigitalSignaturePasswordMessage(rsl);
        return buildSmsRequestDTO(organ, signaturePassword, user, NotificationTypeAlias.DIGITAL_SIGNATURE_PASSWORD, messageText);
    }

    private SmsRequestDTO buildSmsRequestDTO(Organ organ, AdmEntity admEntity, Violator violator, NotificationTypeAlias eventType, String messageText) {
        return ViolatorSmsRequestDTO.builder()
                .message(messageText)
                .mobile(violator.getMobile())
                .organ(organ)
                .entity(admEntity)
                .notificationTypeAlias(eventType)
                .violator(violator)
                .build();
    }

    private SmsRequestDTO buildSmsRequestDTO(Organ organ, AdmEntity admEntity, User user, NotificationTypeAlias eventType, String messageText) {
        return UserSmsRequestDTO.builder()
                .message(messageText)
                .mobile(user.getMobile())
                .organ(organ)
                .entity(admEntity)
                .notificationTypeAlias(eventType)
                .user(user)
                .build();
    }

    @Override
    public Page<SmsFullListResponseDTO> globalSearchByFilter(Map<String, String> filterValues, Pageable pageable) {
        return smsSearchService.findAllFullProjectionByFilter(filterValues, pageable)
                .map(SmsFullListResponseDTO::new);
    }
}
