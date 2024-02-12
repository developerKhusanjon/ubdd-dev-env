package uz.ciasev.ubdd_service.service.pdf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.invoice.PdfInvoiceForCourtDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail.*;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.mib.PdfReturnRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.payment.PdfPaymentDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.payment.PdfPaymentWrapperDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.person_card.PdfPersonCardForCourtDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol.PdfActorsDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol.PdfDamageDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol.PdfEvidenceDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol.PdfProtocolDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.requirement.PdfRequirementDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.requirement.PdfRequirementProtocolDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.termination.PdfTerminationDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorPaymentsResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerType;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticlesProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolRequirementProjection;
import uz.ciasev.ubdd_service.entity.protocol.RepeatabilityPdfProjection;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.ProtocolUbddInsuranceData;
import uz.ciasev.ubdd_service.entity.ubdd_data.ProtocolUbddTexPassData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotPresent;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceOwnerTypeRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.damage.DamageDetailService;
import uz.ciasev.ubdd_service.service.damage.DamageService;
import uz.ciasev.ubdd_service.service.dict.OrganDictionaryService;
import uz.ciasev.ubdd_service.service.dict.article.ArticleDictionaryService;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartDictionaryService;
import uz.ciasev.ubdd_service.service.dict.person.IntoxicationTypeDictionaryService;
import uz.ciasev.ubdd_service.service.dict.person.OccupationDictionaryService;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;
import uz.ciasev.ubdd_service.entity.document.RequirementGeneration;
import uz.ciasev.ubdd_service.service.dict.resolution.TerminationReasonDictionaryService;
import uz.ciasev.ubdd_service.service.evidence.EvidenceService;
import uz.ciasev.ubdd_service.service.execution.ViolatorExecutionService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.invoice.PaymentService;
import uz.ciasev.ubdd_service.service.mib.MibCardMovementReturnRequestService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;
import uz.ciasev.ubdd_service.service.participant.ParticipantDetailService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolUbddDataService;
import uz.ciasev.ubdd_service.service.protocol.RepeatabilityService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;
import uz.ciasev.ubdd_service.service.settings.OrganSettingsService;
import uz.ciasev.ubdd_service.service.user.UserService;
import uz.ciasev.ubdd_service.service.victim.VictimDetailService;
import uz.ciasev.ubdd_service.service.victim.VictimService;
import uz.ciasev.ubdd_service.service.violator.ViolatorDetailService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;
import uz.ciasev.ubdd_service.utils.*;
import uz.ciasev.ubdd_service.utils.generator.QRGenerator;
import uz.ciasev.ubdd_service.utils.types.ArticlePair;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias.GOVERNMENT;
import static uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias.VICTIM;

@Service
@RequiredArgsConstructor
public class PdfModelBuilderServiceImpl implements PdfModelBuilderService {

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm)");

    private static final int FULL_PAYMENT = 1;
    private static final int PARTIAL_PAYMENT = 2;
    private static final int NO_PAYMENTS = 3;

    private final QRGenerator qrGenerator;
    private final AddressService addressService;
    private final AdmCaseService admCaseService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final ProtocolService protocolService;
    private final EvidenceService evidenceService;
    private final PunishmentService punishmentService;
    private final OrganSettingsService organInfoService;
    private final OrganDictionaryService organService;
    private final DamageDetailService damageDetailService;
    private final VictimDetailService victimDetailService;
    private final CompensationService compensationService;
    private final ViolatorDetailService violatorDetailService;
    private final ArticleDictionaryService articleService;
    private final ArticlePartDictionaryService articlePartService;
    private final ParticipantDetailService participantDetailService;
    private final RepeatabilityService violationRepeatabilityService;
    private final UserService userService;
    private final IntoxicationTypeDictionaryService intoxicationTypeService;
    private final OccupationDictionaryService occupationService;
    private final SmsNotificationService smsNotificationService;
    private final ProtocolUbddDataService protocolUbddDataService;
    private final PdfModelPhotoService photoService;
    private final DecisionService decisionService;
    private final ViolatorService violatorService;
    private final ViolatorExecutionService violatorExecutionService;
    private final MibCardMovementReturnRequestService returnRequestService;
    private final TerminationReasonDictionaryService terminationReasonService;
    private final InvoiceOwnerTypeRepository invoiceOwnerTypeRepository;
    private final DamageService damageService;
    private final VictimService victimService;

    @Override
    public PdfDecisionDTO buildPenaltyDecisionModel(Decision decision, DocumentGenerationLog documentGenerationLog) {

        var resolution = decision.getResolution();
        var mainPunishment = decision.getMainPunishment();
        var invoiceOpt = invoiceService.findPenaltyInvoiceByDecision(decision);

        if (decision.getArticlePartId() == null
                || decision.getArticleId() == null
                || mainPunishment == null
                || mainPunishment.getPenalty() == null
                || invoiceOpt.isEmpty())
            throw new KarorPdfDataException();

        var penalty = mainPunishment.getPenalty();
        var penaltyInvoice = invoiceOpt.get();
        var compensation = compensationService
                .findGovByDecision(decision)
                .orElse(null);

        var violator = decision.getViolator();
        var protocol = protocolService.findSingleMainByAdmCaseId(resolution.getAdmCaseId());
        var violatorDetail = protocol.getViolatorDetail();

        var decisionDTO = new PdfDecisionDTO(decision);
        var karorDTO = collectKarorData(resolution, decision);
        var violatorDTO = collectViolatorDataForDecision(violator, violatorDetail, protocol, decision);
        var violationDTO = collectDecisionViolationData(resolution, decision, compensation, mainPunishment, protocol);

        var time = LocalDateTime.now();

        decisionDTO.setCreatedDate(formatDate(time.toLocalDate()));
        decisionDTO.setCreatedTime(formatTime(time));

        decisionDTO.setKaror(karorDTO);
        decisionDTO.setViolator(violatorDTO);
        decisionDTO.setViolation(violationDTO);
        decisionDTO.setInvoice(collectInvoiceData(penaltyInvoice, penalty));

        if (compensation != null) {
            decisionDTO.setDamageInvoice(collectInvoiceData(compensation.getInvoice(), null));
        }

        decisionDTO.setAutoCreateId(
                Optional.ofNullable(documentGenerationLog)
                        .map(dl -> dl.getId().toString())
                        .orElse(null)
        );

        return decisionDTO;
    }

    @Override
    public PdfTerminationDTO buildTerminationDecisionModel(Decision decision, DocumentGenerationLog documentGenerationLog) {

        var resolution = decision.getResolution();

        var consider = userService.findById(resolution.getUserId());

        var violator = decision.getViolator();
        var violatorPerson = violator.getPerson();
        var protocol = protocolService.findSingleMainByAdmCaseId(resolution.getAdmCaseId());

        var terminationReason = terminationReasonService.getById(decision.getTerminationReasonId());
        var resolutionDate = resolution.getResolutionTime();

        var place = resolution.getDistrict() != null
                ? resolution.getRegion().getDefaultName() + ", " + resolution.getDistrict().getDefaultName()
                : resolution.getRegion().getDefaultName();

        var decisionModel = new PdfTerminationDTO(decision);

        var time = LocalDateTime.now();

        decisionModel.setCreatedDate(formatDate(time.toLocalDate()));
        decisionModel.setCreatedTime(formatTime(time));

        decisionModel.setYear(String.valueOf(resolutionDate.getYear()));
        decisionModel.setMonth(MonthConverter.valueOf(resolutionDate.getMonth().name()).getValue());
        decisionModel.setDay(String.valueOf(resolutionDate.getDayOfMonth()));
        decisionModel.setPlace(place);
        decisionModel.setOrganName(protocol.getOrgan().getDefaultName() + ", " + protocol.getInspectorInfo());
        decisionModel.setSecondName(consider.getSecondNameLat());
        decisionModel.setFirstName(consider.getFirstNameLat());
        decisionModel.setLastName(consider.getLastNameLat());
        decisionModel.setArticle(protocol.getArticlePart().getShortName().getLat());
        decisionModel.setViolatorName(violatorPerson.getFIOLat());
        decisionModel.setStateCircumstancesText(terminationReason.getDefaultName());
        decisionModel.setPosition(consider.getPosition().getDefaultName());
        decisionModel.setFullName(consider.getFio());
        decisionModel.setSign(resolution.getConsiderSignature());

        decisionModel.setAutoCreateId(
                Optional.ofNullable(documentGenerationLog)
                        .map(dl -> dl.getId().toString())
                        .orElse(null)
        );

        return decisionModel;
    }

    @Override
    public PdfRequirementDTO buildRequirementModel(RequirementGeneration registration, Supplier<List<ProtocolRequirementProjection>> protocolsSupplier, Supplier<List<ProtocolArticlesProjection>> articlesSupplier) {
//    public PdfRequirementDTO buildRequirementModel(RequirementGeneration registration, Supplier<List<ProtocolRequirementProjection>> protocolsSupplier, Supplier<List<ProtocolArticlesProjection>> articlesSupplier) {

        User user = registration.getUser();

        PdfRequirementDTO requirementModel = new PdfRequirementDTO(registration);

        String titleQr = generateQRCode(String.format("%s\n%s",
                registration.getId(),
                registration.getCreationTime()
        ));
        String signQr = generateQRCode(String.format("%s\n%s\n%s\n%s",
                registration.getId(),
                Optional.ofNullable(user.getOrgan()).map(Organ::getDefaultName).orElse(""),
                user.getInfo(),
                user.getId()
        ));
        String stampQr = generateQRCode(String.format("%s\n%s",
                registration.getId(),
                registration.getSearchParams().toString()
        ));

        requirementModel.setTitleQr(titleQr);
        requirementModel.setSignQr(signQr);
        requirementModel.setStampQr(stampQr);

        requirementModel.setNumber(registration.getId().toString());
        requirementModel.setUserId(user.getId());

        LocalDateTime nowTime = registration.getCreationTime();

        requirementModel.setCreatedDate(formatDate(nowTime.toLocalDate()));
        requirementModel.setCreatedTime(formatTime(nowTime));

        requirementModel.setYear(String.valueOf(nowTime.getYear()));
        requirementModel.setMonth(MonthConverter.valueOf(nowTime.getMonth().name()).getValue());
        requirementModel.setStampDate(String.valueOf(nowTime.getDayOfMonth()));

        OrganInfo organInfo = organInfoService.getOrganInfo(user);
        requirementModel.setHeadOfOrgan(Optional.ofNullable(user.getOrgan()).map(Organ::getDefaultName).orElse(""));
        requirementModel.setExecutor(user.getInfo());
        requirementModel.setPost(organInfo.getPostIndex());
        requirementModel.setAddress(organInfo.getAddress());

//        ProtocolGroupByPersonDTO person = requestDTO.getPerson();
//
//        requirementModel.setLastName(person.getLastNameKir());
//        requirementModel.setFirstName(person.getFirstNameKir());
//        requirementModel.setSecondName(person.getSecondNameKir());
//        requirementModel.setNameLat(String.format("%s %s %s", person.getLastNameLat(), person.getFirstNameLat(), person.getSecondNameLat()));
//        requirementModel.setResidentAddress(person.getActualAddress());
//
//        LocalDate birthDate = person.getBirthDate();

        requirementModel.setLastName(registration.getLastNameKir());
        requirementModel.setFirstName(registration.getFirstNameKir());
        requirementModel.setSecondName(registration.getSecondNameKir());
        requirementModel.setNameLat(String.format("%s %s %s", registration.getLastNameLat(), registration.getFirstNameLat(), registration.getSecondNameLat()));
        requirementModel.setResidentAddress(registration.getActualAddress());

        LocalDate birthDate = registration.getBirthDate();
        if (birthDate != null) {
            requirementModel.setBirthYear(String.valueOf(birthDate.getYear()));
            requirementModel.setBirthMonth(MonthConverter.valueOf(birthDate.getMonth().name()).getValue());
            requirementModel.setBirthDate(String.valueOf(birthDate.getDayOfMonth()));
        }

        // PROTOCOL & DECISIONS

        List<Long> ids = Optional.ofNullable(registration.getProtocols()).orElse(List.of());

        if (ids.isEmpty()) {
            requirementModel.setResponse(null);
        } else {

            List<ProtocolArticlesProjection> protocolArticles = articlesSupplier.get();

            Counter counter = new Counter();

            List<PdfRequirementProtocolDTO> protocolModels = protocolsSupplier.get()
                    .stream()
                    .map(projection -> {

                        PdfRequirementProtocolDTO protocolModel = new PdfRequirementProtocolDTO();

                        protocolModel.setFio(String.format("%s %s %s",
                                projection.getViolatorLastNameLat(),
                                projection.getViolatorFirstNameLat(),
                                projection.getViolatorSecondNameLat()));
                        protocolModel.setBirthDate(formatDate(projection.getViolatorBirthDate()));
                        protocolModel.setOrgan(projection.getOrganName());
                        protocolModel.setPlace(projection.getDistrictName());
                        protocolModel.setNumber(String.format("%s-%s",
                                projection.getProtocolSeries(),
                                projection.getProtocolNumber()
                        ));
                        protocolModel.setConsiderDate(formatDateTime(
                                Optional.ofNullable(projection.getResolutionTime()).orElse(projection.getAdmCaseConsideredTime())
                        ));

                        protocolModel.setRow(counter.nextValue());

                        protocolModel.setRegistrationDate(formatDateTime(projection.getRegistrationTime()));
                        protocolModel.setArticles(
                                protocolArticles.stream()
                                        .filter(pa -> pa.getProtocolId().equals(projection.getProtocolId()))
                                        .map(pa -> sj(
                                                pa.getArticlePartShortName(),
                                                pa.getArticleViolationTypeShortName(),
                                                " "
                                        )).collect(toList())
                        );
                        protocolModel.setStatus(
                                Optional.ofNullable(projection.getDecisionStatus()).orElse(projection.getAdmCaseStatus())
                        );
                        protocolModel.setIs34(Optional.ofNullable(projection.getDecisionIsArticle34()).orElse(false));

                        protocolModel.setDecisionNumber(sj(
                                projection.getDecisionSeries(),
                                projection.getDecisionNumber(),
                                "-"
                        ));
                        protocolModel.setMainPunishment(sj(
                                projection.getMainPunishmentType(),
                                projection.getMainPunishmentAmount(),
                                ": "
                        ));
                        protocolModel.setAdditionalPunishment(sj(
                                projection.getAdditionalPunishmentType(),
                                projection.getAdditionalPunishmentAmount(),
                                ": "
                        ));

                        protocolModel.setCompensation(sjc(
                                projection.getCompensationAmount(),
                                projection.getCompensationPaidAmount(),
                                ", "
                        ));

                        return protocolModel;
                    }).collect(toList());

            requirementModel.setResponse(protocolModels);
        }

        return requirementModel;
    }

    private String sj(Object str1, Object str2, String delimiter) {
        StringJoiner j = new StringJoiner("");
        Optional.ofNullable(str1).map(s -> s.toString()).ifPresent(j::add);
        Optional.ofNullable(str2).map(s -> (j.length() > 0 ? delimiter : "") + s.toString()).ifPresent(j::add);
        return j.toString();
    }

    private String sjc(Long str1, Long str2, String delimiter) {
//        StringJoiner j = new StringJoiner("");
//        Optional.ofNullable(str1).map(MoneyFormatter::coinToString)
//                .map(s -> "Belgilangan zarar: " + s.toString() + " so’m").ifPresent(j::add);
//        Optional.ofNullable(str2).map(MoneyFormatter::coinToString)
//                .map(s -> (j.length() > 0 ? delimiter : "") + "Undirilgan: " + s.toString() + " so’m").ifPresent(j::add);
//        return j.toString();
        long amount = Optional.ofNullable(str1).orElse(0L);
        long amountPaid = Optional.ofNullable(str2).orElse(0L);
        if (amount == 0L && amountPaid == 0L) {
            return "";
        }
        return String.format("Belgilangan zarar: %s so’m, Undirilgan: %s so’m", MoneyFormatter.coinToString(amount), MoneyFormatter.coinToString(amountPaid));
    }

    private static class Counter {
        private Integer it = 1;

        public Integer nextValue() {
            return it++;
        }
    }

    @Override
    public PdfInvoiceForCourtDTO buildInvoiceModel(Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);

        Decision decision = invoiceService.getInvoiceDecision(invoice);
//        if (invoice.getOwnerType().is(PENALTY)) {
//            Punishment punishment = punishmentService.findByPenaltyId(invoice.getPenaltyPunishmentId());
//            decision = decisionService.getDTOById(punishment.getDecision().getId());
//        } else if (invoice.getOwnerType().is(COMPENSATION)) {
//            decision = compensationService.findDecisionByCompensationId(invoice.getCompensationId());
//        } else {
//            throw new InvoiceNotFoundException();
//        }

        PenaltyPunishment penaltyPunishment = InvoiceOwnerTypeAlias.PENALTY.equals(invoice.getOwnerTypeAlias())
                ? decision.getPenalty().orElse(null)
                : null;

        var invoiceForCourt = new PdfInvoiceForCourtDTO(invoice);
        var invoiceModel = collectInvoiceData(invoice, penaltyPunishment);

        var time = LocalDateTime.now();

        invoiceForCourt.setCreatedDate(formatDate(time.toLocalDate()));
        invoiceForCourt.setCreatedTime(formatTime(time));

        invoiceForCourt.setSeries(decision.getSeries());
        invoiceForCourt.setNumber(decision.getNumber());
        invoiceForCourt.setOwnerType(invoice.getOwnerTypeAlias().name());
        invoiceForCourt.setDate(formatDate(decision.getCreatedTime().toLocalDate()));
        invoiceForCourt.setTime(formatTime(decision.getCreatedTime()));
        invoiceForCourt.setInvoice(invoiceModel);

        return invoiceForCourt;
    }

    @Override
    public PdfPaymentWrapperDTO buildPaymentModel(Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        List<Payment> payments = paymentService.findByInvoiceId(invoiceId);
        Decision decision = invoiceService.getInvoiceDecision(invoice);
        Optional<Article> article = Optional.ofNullable(decision.getArticleId()).map(articleService::getById);
        Optional<ArticlePart> articlePart = Optional.ofNullable(decision.getArticlePartId()).map(articlePartService::getById);
        PdfPaymentWrapperDTO paymentWrapper = new PdfPaymentWrapperDTO(invoice);

        LocalDateTime time = LocalDateTime.now();
        paymentWrapper.setCreatedDate(formatDate(time.toLocalDate()));
        paymentWrapper.setCreatedTime(formatTime(time));

        paymentWrapper.setFio(invoice.getPayerName());
        paymentWrapper.setBirthDate(formatDate(invoice.getPayerBirthdate()));
        paymentWrapper.setArticle(article.map(Article::getNumber).map(String::valueOf).orElse(null));
        paymentWrapper.setPrim(article.map(Article::getPrim).map(String::valueOf).orElse(null));
        paymentWrapper.setPart(articlePart.map(ArticlePart::getNumber).map(String::valueOf).orElse(null));
        paymentWrapper.setAmount(String.valueOf(MoneyFormatter.extractCurrency(invoice.getAmount())));
        paymentWrapper.setAmountText(MoneyFormatter.toWord(invoice.getAmount()));
        paymentWrapper.setInvoiceType(calculateInvoiceType(invoice.getOwnerTypeAlias()));
        paymentWrapper.setStatus(calculateInvoiceStatus(invoice));

        if (payments != null) {
            ArrayList<PdfPaymentDTO> list = new ArrayList<>();
            for (Payment payment : payments) {
                PdfPaymentDTO paymentModel = new PdfPaymentDTO();
                paymentModel.setNumber(payment.getNumber());
                paymentModel.setDate(formatDate(payment.getCreatedTime().toLocalDate()));
                paymentModel.setTime(formatTime(payment.getCreatedTime()));
                paymentModel.setAmount(MoneyFormatter.coinToString(payment.getAmount()));
                paymentModel.setAmountText(MoneyFormatter.toWord(payment.getAmount()));

                paymentModel.setFromBankCode(payment.getFromBankCode());
                paymentModel.setFromBankAccount(payment.getFromBankAccount());
                paymentModel.setFromBankName(payment.getFromBankName());
                paymentModel.setFromInn(payment.getFromInn());

                paymentModel.setToBankCode(payment.getToBankCode());
                paymentModel.setToBankAccount(payment.getToBankAccount());
                paymentModel.setToBankName(payment.getToBankName());
                paymentModel.setToInn(payment.getToInn());

                list.add(paymentModel);
            }
            paymentWrapper.setPayments(list);
        }

        return paymentWrapper;
    }

    @Override
    public PdfPaymentWrapperDTO buildViolatorPaymentModel(Long violatorId, InvoiceOwnerTypeAlias invoiceOwnerType) {
        Violator violator = violatorService.getById(violatorId);
        Decision decision = decisionService.findActiveByViolatorId(violatorId)
                .orElseThrow(() -> new ValidationException(ErrorCode.ACTIVE_DECISION_DOES_NOT_EXIST_FOR_VIOLATOR));

        AdmStatusAlias statusAlias;
        ArticlePart articlePart;
        Long paymentAmount;

        if (invoiceOwnerType == InvoiceOwnerTypeAlias.PENALTY) {
            PenaltyPunishment penaltyPunishment = decision.getPenalty()
                    .orElseThrow(() -> new ValidationException(ErrorCode.ACTIVE_DECISION_HAS_NO_PENALTY));
            statusAlias = decision.getMainPunishment().getStatus().getAlias();
            paymentAmount = penaltyPunishment.getAmount();
        } else if (invoiceOwnerType == InvoiceOwnerTypeAlias.COMPENSATION) {
            Compensation compensation = compensationService.findGovByDecision(decision)
                    .orElseThrow(() -> new ValidationException(ErrorCode.ACTIVE_DECISION_HAS_NO_COMPENSATION));
            statusAlias = compensation.getStatus().getAlias();
            paymentAmount = compensation.getAmount();
        } else {
            throw new NotImplementedException(String.format("Violator payment for %s", invoiceOwnerType));
        }

        if (decision.getArticlePartId() != null) {
            articlePart = articlePartService.getById(decision.getArticlePartId());
        } else {
//            Protocol protocol = protocolService.getMainByViolatorId(violatorId);
//            article = protocol.getArticle();
//            articlePart = protocol.getArticlePart();
            articlePart = protocolService.getViolatorMainArticle(violator);
        }
        Article article = articlePart.getArticle();

        List<ViolatorPaymentsResponseDTO> payments = violatorExecutionService.
                findAllPaymentsDTOById(violatorId, invoiceOwnerType);

        PdfPaymentWrapperDTO paymentWrapper = new PdfPaymentWrapperDTO(decision);
        LocalDateTime currentTime = LocalDateTime.now();

        paymentWrapper.setCreatedDate(formatDate(currentTime.toLocalDate()));
        paymentWrapper.setCreatedTime(formatTime(currentTime));

        paymentWrapper.setFio(violator.getPerson().getFIOLat());
        paymentWrapper.setBirthDate(formatDate(violator.getPerson().getBirthDate()));

        paymentWrapper.setArticle(String.valueOf(article.getNumber()));
        paymentWrapper.setPrim(String.valueOf(article.getPrim()));
        paymentWrapper.setPart(String.valueOf(articlePart.getNumber()));

        paymentWrapper.setAmount(MoneyFormatter.coinToString(paymentAmount));
        paymentWrapper.setAmountText(MoneyFormatter.toWord(paymentAmount));

        paymentWrapper.setInvoiceType(calculateInvoiceType(invoiceOwnerType));
        paymentWrapper.setStatus(determinePaymentStatus(statusAlias));

        if (!payments.isEmpty()) {
            ArrayList<PdfPaymentDTO> list = new ArrayList<PdfPaymentDTO>();

            for (ViolatorPaymentsResponseDTO payment : payments) {
                PdfPaymentDTO paymentModel = new PdfPaymentDTO();

                paymentModel.setNumber(payment.getNumber());
                paymentModel.setDate(formatDate(payment.getPaymentTime().toLocalDate()));
                paymentModel.setTime(formatTime(payment.getPaymentTime()));
                paymentModel.setAmount(MoneyFormatter.coinToString(payment.getAmount()));
                paymentModel.setAmountText(MoneyFormatter.toWord(payment.getAmount()));
                paymentModel.setIsBilling(payment.getIsBilling());

                paymentModel.setFromBankCode(payment.getFromBankCode());
                paymentModel.setFromBankAccount(payment.getFromBankAccount());
                paymentModel.setFromBankName(payment.getFromBankName());
                paymentModel.setFromInn(payment.getFromInn());

                paymentModel.setToBankCode(payment.getToBankCode());
                paymentModel.setToBankAccount(payment.getToBankAccount());
                paymentModel.setToBankName(payment.getToBankName());
                paymentModel.setToInn(payment.getToInn());

                list.add(paymentModel);
            }
            paymentWrapper.setPayments(list);
        }

        return paymentWrapper;
    }

    @Override
    public PdfProtocolDTO buildProtocolModel(Long protocolId) {
        var protocol = protocolService.findById(protocolId);
        var admCase = admCaseService.getByProtocolId(protocolId);
//        var user = protocol.getUser();
//        var inspector = user.getPerson();
        var violatorDetail = protocol.getViolatorDetail();
        var violator = violatorDetail.getViolator();

        var violationAddress = FormatUtils.getDisplayNameOrDefault(protocol.getRegion()) +
                " " +
                FormatUtils.getDisplayNameOrDefault(protocol.getDistrict());

        var organPlace = FormatUtils.getDisplayNameOrDefault(protocol.getInspectorRegion()) +
                " " +
                FormatUtils.getDisplayNameOrDefault(protocol.getInspectorDistrict());

        var victims = collectVictimData(protocolId);
        var participants = collectParticipantData(protocolId);

        List<PdfActorsDTO> actors = Stream.of(victims, participants)
                .flatMap(Collection::stream)
                .collect(toList());

        var protocolModel = new PdfProtocolDTO(protocol);

        var time = LocalDateTime.now();

        protocolModel.setCreatedDate(formatDate(time.toLocalDate()));
        protocolModel.setCreatedTime(formatTime(time));

        protocolModel.setProtocolQr(generateQRCode(protocol.getSeries() + protocol.getNumber()));
        protocolModel.setProtocolSeries(protocol.getSeries());
        protocolModel.setProtocolNumber(protocol.getNumber());
        protocolModel.setProtocolCreatedDate(formatDate(protocol.getCreatedTime().toLocalDate()));
        protocolModel.setViolationAddress(violationAddress);

        protocolModel.setOrgan(protocol.getOrgan().getDefaultName());
        protocolModel.setOrganLogo(photoService.getProtocolOrganPhoto(protocol));
//        protocolModel.setOrganLogo(getFileFromStorage(protocol.getOrgan().getLogoPath()));
        protocolModel.setOrganPlace(organPlace);
        protocolModel.setInspectorName(protocol.getInspectorFio());
        protocolModel.setInspectorRank(protocol.getInspectorRank().getDefaultName());
        protocolModel.setInspectorPosition(protocol.getInspectorPosition().getDefaultName());
        protocolModel.setInspectorWorkCertificate(protocol.getInspectorWorkCertificate());
        protocolModel.setInspectorSign(protocol.getInspectorSignature());

        var violatorModel = collectViolatorDataForProtocol(violator, violatorDetail, protocol);
        var violationModel = collectViolationDataForProtocol(admCase.getId(), violator, protocol);

        protocolModel.setExplanatory(protocol.getExplanatory());
        protocolModel.setFabula(protocol.getFabula());

        protocolModel.setViolator(violatorModel);
        protocolModel.setViolation(violationModel);

        protocolModel.setActors(actors);

        protocolModel.setDamage(collectProtocolDamages(protocolId));
        protocolModel.setEvidences(collectEvidenceData(admCase.getId()));

        return protocolModel;
    }

    @Override
    public PdfMailDTO buildMailModel(Decision decision, OrganInfo organInfo, String number) {
        var resolution = decision.getResolution();
        var mainPunishment = decision.getMainPunishment();
        var invoiceOpt = invoiceService.findPenaltyInvoiceByDecision(decision);

        if (mainPunishment == null || mainPunishment.getPenalty() == null || invoiceOpt.isEmpty())
            throw new KarorPdfDataException();

        var penalty = mainPunishment.getPenalty();
        var penaltyInvoice = invoiceOpt.get();
        var compensation = compensationService
                .findGovByDecision(decision)
                .orElse(null);

        var violator = decision.getViolator();
        var protocol = protocolService.findSingleMainByAdmCaseId(resolution.getAdmCaseId());
        var violatorDetail = protocol.getViolatorDetail();

        var mailModel = new PdfMailDTO(decision);
        var karorModel = collectKarorData(resolution, decision);
        var violatorModel = collectViolatorDataForDecision(violator, violatorDetail, protocol, decision);
        var violationModel = collectDecisionViolationData(resolution, decision, compensation, mainPunishment, protocol);
        var postModel = collectPostData(resolution, decision, organInfo, number);

        var time = LocalDateTime.now();

        mailModel.setCreatedDate(formatDate(time.toLocalDate()));
        mailModel.setCreatedTime(formatTime(time));
        mailModel.setPost(postModel);
        mailModel.setKaror(karorModel);
        mailModel.setViolator(violatorModel);
        mailModel.setViolation(violationModel);
        mailModel.setInvoice(collectInvoiceData(penaltyInvoice, penalty));

        if (compensation != null)
            mailModel.setDamageInvoice(collectInvoiceData(compensation.getInvoice(), null));

        return mailModel;
    }

    @Override
    public PdfPersonCardForCourtDTO buildPersonCardModel(Long violatorDetailId) {
        ViolatorDetail violatorDetail = violatorDetailService.findById(violatorDetailId);
        Person person = violatorDetail.getViolator().getPerson();
        Address personBirthAddress = addressService.findById(person.getBirthAddressId());
        Address documentGivenAddress = addressService.findById(violatorDetail.getDocumentGivenAddressId());
        Address residenceAddress = Optional.ofNullable(violatorDetail.getResidenceAddressId()).map(addressService::findById).orElse(null);

        var personCard = new PdfPersonCardForCourtDTO(violatorDetail);

        var time = LocalDateTime.now();

        personCard.setCreatedDate(formatDate(time.toLocalDate()));
        personCard.setCreatedTime(formatTime(time));

        if (person.isRealPinpp())
            personCard.setPinpp(person.getPinpp());

        personCard.setFullName(person.getFIOLat());
        personCard.setBirthDate(formatDate(person.getBirthDate()));
        personCard.setBirthAddress(FormatUtils.addressToText(personBirthAddress));
        personCard.setGender(person.getGender().getDefaultName());
        personCard.setNationality(person.getNationality().getDefaultName());
        personCard.setCitizenship(person.getCitizenshipType().getDefaultName());
        personCard.setResidenceAddress(FormatUtils.addressToText(residenceAddress));
        personCard.setDocumentType(violatorDetail.getPersonDocumentType().getDefaultName());
        personCard.setDocumentSeriesAndNumber(violatorDetail.getDocumentSeries() + " " + violatorDetail.getDocumentNumber());
        personCard.setGivingDate(formatDate(violatorDetail.getDocumentGivenDate()));
        personCard.setGivingAddress(FormatUtils.addressToText(documentGivenAddress));
        personCard.setExpiresAt(formatDate(violatorDetail.getDocumentExpireDate()));
        personCard.setPhotoUrl(photoService.getViolatorProto(violatorDetail.getViolator()));

        return personCard;
    }

    private List<PdfActorsDTO> collectVictimData(Long protocolId) {
        var victimDetails = victimDetailService.findAllByProtocolId(protocolId);

        if (victimDetails == null || victimDetails.isEmpty())
            return Collections.emptyList();

        var victimsDTO = new ArrayList<PdfActorsDTO>();

        for (var victimDetail : victimDetails) {
            var person = victimDetail.getVictim().getPerson();

            var victimModel = new PdfActorsDTO();

            victimModel.setFio(buildFio(person));
            victimModel.setLastName(person.getLastNameLat());
            victimModel.setFirstName(person.getFirstNameLat());
            victimModel.setSecondName(person.getSecondNameLat());
            victimModel.setBirthDate(formatDate(person.getBirthDate()));
            victimModel.setSign(victimDetail.getSignature());
            victimModel.setSignDate(formatDate(victimDetail.getCreatedTime().toLocalDate()));
            victimModel.setIsVictim(true);

            victimsDTO.add(victimModel);
        }
        return victimsDTO;
    }

    private List<PdfActorsDTO> collectParticipantData(Long protocolId) {
        var participantDetails = participantDetailService.findAllByProtocolId(protocolId);

        if (participantDetails == null || participantDetails.isEmpty())
            return Collections.emptyList();

        var participantsDTO = new ArrayList<PdfActorsDTO>();

        for (var participantDetail : participantDetails) {
            var person = participantDetail.getParticipant().getPerson();

            var participantModel = new PdfActorsDTO();

            participantModel.setFio(buildFio(person));
            participantModel.setLastName(person.getLastNameLat());
            participantModel.setFirstName(person.getFirstNameLat());
            participantModel.setSecondName(person.getSecondNameLat());
            participantModel.setBirthDate(formatDate(person.getBirthDate()));
            participantModel.setSign(participantDetail.getSignature());
            participantModel.setSignDate(formatDate(participantDetail.getCreatedTime().toLocalDate()));

            participantsDTO.add(participantModel);
        }
        return participantsDTO;
    }

    private List<PdfEvidenceDTO> collectEvidenceData(Long caseId) {
        return evidenceService.findAllByAdmCaseId(caseId)
                .stream()
                .filter(Objects::nonNull)
                .map(PdfEvidenceDTO::new)
                .collect(toList());
    }

    private List<PdfDamageDTO> collectProtocolDamages(Long id) {
        List<DamageDetail> damageDetails = damageDetailService.findAllByProtocolId(id);
        List<PdfDamageDTO> result = new ArrayList<>();
        Map<Long, PdfDamageDTO> duplicates = new HashMap<>();

        for (DamageDetail damageDetail : damageDetails) {
            PdfDamageDTO dto = new PdfDamageDTO();
            Damage damage = damageService.findById(damageDetail.getDamageId());
            Long curAmount = MoneyFormatter.extractCurrency(damageDetail.getAmount());

            if (damage.getVictimType().getAlias().equals(GOVERNMENT)) {
                dto.setName("В пользу государства");
                dto.setVictimType(damage.getVictimType().getAlias());
                dto.setAmount(curAmount);
                result.add(dto);

            } else if (damage.getVictimType().getAlias().equals(VICTIM)) {
                Person person = victimService.findById(damage.getVictimId()).getPerson();

                dto.setName(buildFio(person));
                dto.setVictimType(damage.getVictimType().getAlias());

                PdfDamageDTO duplicate = duplicates.get(person.getId());
                if (duplicate != null) {
                    Long amount = duplicate.getAmount() == null ? 0L : duplicate.getAmount();
                    dto.setAmount(amount + curAmount);
                } else {
                    dto.setAmount(curAmount);
                }
                duplicates.put(person.getId(), dto);
            }
        }
        if (!duplicates.isEmpty())
            result.addAll(new ArrayList<>(duplicates.values()));

        return result;
    }

    private PdfViolatorDTO collectViolatorDataBase(Violator violator, ViolatorDetail vDetail, Protocol protocol, Supplier<List<RepeatabilityPdfProjection>> repeatability) {
        var person = violator.getPerson();
        var personBirthAddress = addressService.findById(person.getBirthAddressId());
        var residentAddress = addressService.findById(violator.getActualAddressId());
        var protocolRepeatabilityInfo = FormatUtils.violationRepeatabilityToText(repeatability.get());
        var intoxicationType = Optional.ofNullable(vDetail.getIntoxicationTypeId()).map(intoxicationTypeService::getById).orElse(null);
        var occupation = occupationService.getById(vDetail.getOccupationId());

        var violatorOccupationInfo = String.join(" ",
                occupation.getDefaultName(),
                Optional.ofNullable(vDetail.getEmploymentPlace()).orElse("yo'q"),
                Optional.ofNullable(vDetail.getEmploymentPosition()).orElse("yo'q"));

        var violatorModel = new PdfViolatorDTO();

        violatorModel.setPhoto(photoService.getViolatorProto(violator));
        violatorModel.setFullName(person.getFIOLat());
        violatorModel.setBirthDate(person.getBirthDate().format(dateFormat));
        violatorModel.setPhone(FormatUtils.mobileToText(violator.getMobile()));
        violatorModel.setBirthPlace(FormatUtils.addressToText(personBirthAddress));
        violatorModel.setDocumentSeriesAndNumber(vDetail.getDocumentSeries() + " " + vDetail.getDocumentNumber());
        violatorModel.setNationality(person.getNationality().getDefaultName());
        violatorModel.setCitizenship(person.getCitizenshipType().getDefaultName());
        violatorModel.setResidentAddress(FormatUtils.addressToText(residentAddress));
        violatorModel.setOccupation(violatorOccupationInfo);
        violatorModel.setIntoxication(FormatUtils.getDisplayNameOrDefault(intoxicationType, "yo'q"));
        violatorModel.setRepeatability(protocolRepeatabilityInfo);
        violatorModel.setAdditionalInfo(Optional.ofNullable(vDetail.getAdditionally()).orElse("yo'q"));

        setUbddData(protocol, violatorModel);

        violatorModel.setPinpp(person.isRealPinpp() ? person.getPinpp() : "-");

        return violatorModel;
    }

    private void setUbddData(Protocol protocol, PdfViolatorDTO violatorModel) {
//        var ubddDataOpt = protocolUbddDataService.getByProtocolId(protocol.getId());
//        ubddDataOpt.ifPresent(ubddData -> {
//            violatorModel.setInsurance(Optional.ofNullable(ubddData.getInsuranceNumber()).orElse("yo'q"));
//            violatorModel.setTransport(UbddFormatUtils.buildTransport(ubddData));
//        });

        Optional<ProtocolUbddTexPassData> texPassDataOpt = protocolUbddDataService.findTexPassByProtocolId(protocol.getId());
        Optional<ProtocolUbddInsuranceData> insuranceDataOpt = protocolUbddDataService.findInsuranceByProtocolId(protocol.getId());

        String insurance = insuranceDataOpt.map(i -> i.getPolicySeries() + i.getPolicyNumber()).orElse("yo'q");
        String transport = texPassDataOpt.map(tp -> UbddFormatUtils.buildTransport(protocol, tp)).orElse("");

        violatorModel.setInsurance(insurance);
        violatorModel.setTransport(transport);


    }

    private PdfViolatorDTO collectViolatorDataForProtocol(Violator violator, ViolatorDetail vDetail, Protocol protocol) {
        return collectViolatorDataBase(violator, vDetail, protocol, () -> violationRepeatabilityService.findRepeatabilityForProtocolPdf(protocol));
    }

    private PdfViolatorDTO collectViolatorDataForDecision(Violator violator, ViolatorDetail vDetail, Protocol mainProtocol, Decision decision) {
        return collectViolatorDataBase(violator, vDetail, mainProtocol, () -> violationRepeatabilityService.findRepeatabilityForDecisionPdf(decision, mainProtocol));
    }

    private PdfPostDataDTO collectPostData(Resolution resolution, Decision decision, OrganInfo organInfo, String number) {
        Violator violator = decision.getViolator();
        Person person = violator.getPerson();
        Optional<Organ> organOptional = Optional.ofNullable(organInfo.getOrganId()).map(organService::getById);
        Address postAddress = addressService.findById(violator.getPostAddressId());

        PdfPostDataDTO postModel = new PdfPostDataDTO();

        postModel.setOrgan(organOptional.map(Organ::getDefaultName).orElseThrow(() -> new ServerException(ErrorCode.EMPTY_ORGAN_IN_ORGAN_INFO)));
        postModel.setPostAddressFrom(organInfo.getAddress());
        postModel.setPostPhoneFrom(organInfo.getLandline());
        postModel.setPostAddressToName(person.getFIOLat());
        postModel.setPostAddressTo(FormatUtils.addressToText(postAddress));
        postModel.setPostPhoneTo(FormatUtils.mobileToText(violator.getMobile()));
        postModel.setPostBarcode(generateBarcode("EMI" + number));
        postModel.setDate(formatDate(LocalDate.now()));
        postModel.setTime(formatTime(LocalDateTime.now()));
        postModel.setPostNumber("EMI" + number);

        return postModel;
    }

    private PdfViolationDTO collectDecisionViolationData(Resolution resolution,
                                                         Decision decision,
                                                         Compensation compensation,
                                                         Punishment mainPunishment,
                                                         Protocol protocol) {

        Violator violator = decision.getViolator();
        ViolatorDetail violatorDetail = protocol.getViolatorDetail();
        Long fineAmount = mainPunishment.getPenalty().getAmount();

        Optional<Article> article = Optional.ofNullable(decision.getArticleId()).map(articleService::getById);
        Optional<ArticlePart> articlePart = Optional.ofNullable(decision.getArticlePartId()).map(articlePartService::getById);

        String additionPunishmentInfo = Optional
                .ofNullable(decision.getAdditionPunishment())
                .map(FormatUtils::punishmentToString)
                .orElse("yo'q");
        List<String> additionArticles = protocolService.getViolatorArticleParts(violator)
                .stream()
                .filter(ap -> !ap.getId().equals(decision.getArticlePartId()))
                .map(ArticlePart::getShortName)
                .map(MultiLanguage::getLat)
                .collect(toList());

        PdfViolationDTO violationModel = new PdfViolationDTO();
        violationModel.setFingerprint(violatorDetail.getSignature());
        violationModel.setAgreeFingerprint(violatorDetail.getSignature());
        violationModel.setArticle(article.map(Article::getNumber).map(String::valueOf).orElse(null));
        violationModel.setPrim(article.map(Article::getPrim).map(String::valueOf).orElse(null));
        violationModel.setPart(articlePart.map(ArticlePart::getNumber).map(String::valueOf).orElse(null));
        violationModel.setViolationType(Optional.ofNullable(protocol.getArticleViolationType()).map(ArticleViolationType::getDefaultName).orElse(null));
        violationModel.setAdditionalArticles(additionArticles);

        violationModel.set33(decision.isArticle33());
        violationModel.set34(decision.isArticle34());
        violationModel.setSmsNotify(violator.isNotificationViaSms());
        violationModel.setPostNotify(violator.isNotificationViaMail());
        violationModel.setAgree(protocol.isAgree());
        violationModel.setSignDate(formatDate(protocol.getRegistrationTime().toLocalDate()));
//        violationModel.setPunishmentText("йўк");
//        violationModel.setPunishmentNum("йўк");
        violationModel.setAmount(MoneyFormatter.coinToString(fineAmount));
        violationModel.setAmountText(MoneyFormatter.toWord(fineAmount));
        violationModel.setArrest(additionPunishmentInfo);
        violationModel.setViolationDate(formatDate(protocol.getViolationTime().toLocalDate()));
        violationModel.setViolationTime(formatTime(protocol.getViolationTime()));
        violationModel.setViolatorSignature(violatorDetail.getSignature());
        violationModel.setViolatorSignDate(formatDate(violatorDetail.getCreatedTime().toLocalDate()));

        if (compensation != null) {
            violationModel.setDamageAmount(MoneyFormatter.coinToString(compensation.getAmount()));
            violationModel.setDamageAmountText(MoneyFormatter.toWord(compensation.getAmount()));
        } else {
            violationModel.setDamageAmountText("");
        }

        setScenePhotos(violationModel, resolution.getAdmCaseId());

        return violationModel;
    }

    private void setScenePhotos(PdfViolationDTO violationModel, Long admCaseId) {
        var placeProtoUri = photoService.getScenePhotos(admCaseId);

        if (!placeProtoUri.isEmpty()) {
            violationModel.setPhoto1(placeProtoUri.get(0));
            if (placeProtoUri.size() > 1) {
                violationModel.setPhoto2(placeProtoUri.get(1));
            }
        }
    }

    private int calculateInvoiceStatus(Invoice invoice) {

        AdmStatusAlias statusAlias = null;

        switch (invoice.getOwnerTypeAlias()) {
            case PENALTY: {
                Punishment punishment = punishmentService.findByPenaltyId(invoice.getPenaltyPunishmentId());
                statusAlias = punishment.getStatus().getAlias();
                break;
            }
            case COMPENSATION: {
                Compensation compensation = compensationService.findById(invoice.getCompensationId());
                statusAlias = compensation.getStatus().getAlias();
                break;
            }
            case DAMAGE: {
                throw new NotImplementedException("Calculate invoice status for damage");
            }
            default: {
                throw new KarorPdfDataException();
            }
        }

        switch (statusAlias) {
            case DECISION_MADE:
                return NO_PAYMENTS;
            case IN_EXECUTION_PROCESS:
                return PARTIAL_PAYMENT;
            case EXECUTED:
                return FULL_PAYMENT;
            default: {
                throw new KarorPdfDataException();
            }
        }
    }

    private int determinePaymentStatus(AdmStatusAlias statusAlias) {
        switch (statusAlias) {
            case DECISION_MADE:
                return NO_PAYMENTS;
            case IN_EXECUTION_PROCESS:
                return PARTIAL_PAYMENT;
            case EXECUTED:
                return FULL_PAYMENT;
            default: {
                throw new KarorPdfDataException();
            }
        }
    }

    private String calculateInvoiceType(InvoiceOwnerTypeAlias type) {
        String result;
        switch (type) {
            case PENALTY:
                result = "Jarima";
                break;
            case COMPENSATION:
                result = "Zarar";
                break;
            case DAMAGE:
                result = "Zarar 1";
                break;
            default:
                result = "";
        }
        return result;
    }

    private PdfViolationDTO collectViolationDataForProtocol(Long caseId, Violator violator, Protocol protocol) {

        var violatorDetail = protocol.getViolatorDetail();

        var violationModel = new PdfViolationDTO();
        violationModel.setFingerprint(violatorDetail.getSignature());
        violationModel.setAgreeFingerprint(violatorDetail.getSignature());
        violationModel.setArticle(String.valueOf(protocol.getArticle().getNumber()));
        violationModel.setPrim(String.valueOf(protocol.getArticle().getPrim()));
        violationModel.setPart(String.valueOf(protocol.getArticlePart().getNumber()));
        violationModel.setViolationType(Optional.ofNullable(protocol.getArticleViolationType()).map(ArticleViolationType::getDefaultName).orElse(null));
        violationModel.setAdditionalArticles(collectAdditionalArticles(protocolService.getProtocolAdditionArticles(protocol.getId())));

        violationModel.setSmsNotify(violator.isNotificationViaSms());
        violationModel.setPostNotify(violator.isNotificationViaMail());
        violationModel.setAgree(protocol.isAgree());
        violationModel.setSignDate(formatDate(protocol.getRegistrationTime().toLocalDate()));
        violationModel.setViolationDate(formatDate(protocol.getViolationTime().toLocalDate()));
        violationModel.setViolationTime(formatTime(protocol.getViolationTime()));
        violationModel.setViolatorSignature(violatorDetail.getSignature());
        violationModel.setViolatorSignDate(formatDate(violatorDetail.getCreatedTime().toLocalDate()));

        // В протаколе они не отображаються
        // setScenePhotos(violationModel, caseId);

        return violationModel;
    }

    private PdfKarorDTO collectKarorData(Resolution resolution, Decision decision) {
        var consider = userService.findById(resolution.getUserId());

        var karorModel = new PdfKarorDTO();
        var organPlace = FormatUtils.getDisplayNameOrDefault(resolution.getRegion()) + " " +
                FormatUtils.getDisplayNameOrDefault(resolution.getDistrict());

        karorModel.setQr(generateQRCode(decision.getSeries() + decision.getNumber()));
        karorModel.setSeries(decision.getSeries());
        karorModel.setNumber(decision.getNumber());
        karorModel.setProtocolDate(formatDate(decision.getResolution().getResolutionTime().toLocalDate()));
        karorModel.setProtocolTime(formatTime(decision.getResolution().getResolutionTime()));
        karorModel.setOrgan(resolution.getOrgan().getDefaultName());
        karorModel.setOrganLogo(photoService.getResolutionOrganLogo(resolution));
        karorModel.setOrganPlace(organPlace);
        karorModel.setOrganInspectorName(consider.getFio());
        karorModel.setOrganInspectorRank(consider.getRank().getDefaultName());
        karorModel.setOrganInspectorPosition(consider.getPosition().getDefaultName());
        karorModel.setWorkCertificate(consider.getWorkCertificate());
        karorModel.setOrganInspectorSign(resolution.getConsiderSignature());

        return karorModel;
    }

    private PdfInvoiceDTO collectInvoiceData(Invoice invoice, @Nullable PenaltyPunishment penaltyPunishment) {
        if (invoice == null)
            return null;

        InvoiceOwnerType invoiceOwnerType = invoiceOwnerTypeRepository.findById(invoice.getOwnerTypeId())
                .orElseThrow(() -> new EntityByIdNotPresent(InvoiceOwnerType.class, invoice.getOwnerTypeId()));

        var model = new PdfInvoiceDTO();

        model.setQr(generateQRCode(invoice.getInvoiceSerial()));
        model.setPaymentType(invoiceOwnerType.getName().getLat());
        model.setOrgan(invoice.getOrganName());
        model.setInn(invoice.getBankInn());
        model.setBank(invoice.getBankName());
        model.setBankCode(invoice.getBankCode());
        model.setAccount(invoice.getBankAccount());
        model.setTreasuryAccount(invoice.getTreasuryAccount());
        model.setPayer(invoice.getPayerName());
        model.setBirthDate(formatDate(invoice.getPayerBirthdate()));
        model.setPayerAddress(invoice.getPayerAddress());
        model.setPayerInn(invoice.getPayerInn());
        model.setDate(formatDate(invoice.getInvoiceDate()));

        model.setAmount(MoneyFormatter.coinToString(invoice.getAmount()));
        model.setAmountText(MoneyFormatter.toWord(invoice.getAmount()));
        model.setNumber(invoice.getInvoiceSerial());

        if (penaltyPunishment != null) {
            if (penaltyPunishment.getIsDiscount50()) {
                model.setDiscount50(true);
                model.setDiscount50Date(formatDate(penaltyPunishment.getDiscount50ForDate()));
                model.setDiscount50Amount(MoneyFormatter.coinToString(penaltyPunishment.getDiscount50Amount()));
                model.setDiscount50AmountText(MoneyFormatter.toWord(penaltyPunishment.getDiscount50Amount()));
            }
            if (penaltyPunishment.getIsDiscount70()) {
                model.setDiscount70(true);
                model.setDiscount70Date(formatDate(penaltyPunishment.getDiscount70ForDate()));
                model.setDiscount70Amount(MoneyFormatter.coinToString(penaltyPunishment.getDiscount70Amount()));
                model.setDiscount70AmountText(MoneyFormatter.toWord(penaltyPunishment.getDiscount70Amount()));
            }
        }

        return model;
    }

    private List<String> collectAdditionalArticles(List<? extends ArticlePair> additionalArticleParts) {
        if (additionalArticleParts != null && !additionalArticleParts.isEmpty()) {

            List<String> partNames = additionalArticleParts
                    .stream()
                    .map(this::convertToText)
                    .collect(toList());

            return partNames;
        }
        return List.of();
    }

    private String convertToText(ArticlePair articlePair) {
        ArticlePart part = articlePartService.getById(articlePair.getArticlePartId());
        return part.getDefaultName();
//        if (articlePair.getArticleViolationTypeId() != null) {
//            ArticleViolationType violationType = articleViolationTypeService.getDTOById(articlePair.getArticleViolationTypeId());
//            return String.join(" ", part.getDefaultName(), violationType.getDefaultName());
//        } else {
//            return part.getDefaultName();
//        }
    }

    private String formatDate(LocalDate date) {
        return (date != null)
                ? date.format(dateFormat)
                : null;
    }

    private String formatTime(LocalDateTime localDateTime) {
        return (localDateTime != null)
                ? localDateTime.format(timeFormat)
                : null;
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        return (localDateTime != null)
                ? localDateTime.format(dateTimeFormat)
                : null;
    }

    private String buildFio(Person person) {
        String lastName = person.getLastNameLat();
        String firstName = person.getFirstNameLat();
        String secondName = person.getSecondNameLat();
        // todo отрефакторить
        if (secondName != null && secondName.length() > 0)
            secondName = secondName.charAt(0) + ".";
        else
            secondName = "";

        return String.join(" ", lastName, firstName.charAt(0) + ".", secondName);
    }

    private String generateBarcode(String text) {
//        var series = decision.getSeries();
//        var number = decision.getNumber();
//        var text = String.join(" ", series, number);

        return qrGenerator.generateBarcode(text, 200, 50);
    }

    private String generateQRCode(String text) {
        return qrGenerator.generateQRCode(text, 250, 250);
    }

    @Override
    public PdfSmsDTO buildSmsModel(Long smsNotificationId) {

        SmsNotification sms = smsNotificationService.getById(smsNotificationId);

        PdfSmsDTO rsl = new PdfSmsDTO(sms);

        rsl.setCreatedDate(formatDate(LocalDate.now()));
        rsl.setCreatedTime(formatTime(LocalDateTime.now()));

        rsl.setSendDate(formatDate(
                Optional.ofNullable(sms.getSendTime()).map(LocalDateTime::toLocalDate).orElse(null)
        ));
        rsl.setReceiveDate(formatDate(
                Optional.ofNullable(sms.getReceiveTime()).map(LocalDateTime::toLocalDate).orElse(null)
        ));

        rsl.setPhoneNumber(sms.getPhoneNumber());
        rsl.setSmsText(sms.getMessage());

        return rsl;
    }

    @Override
    public PdfReturnRequestDTO buildMibReturnRequestModel(Long returnRequestId) {
        MibCardMovementReturnRequest returnRequest = returnRequestService.getById(returnRequestId);
        User user = returnRequest.getUser();
        Decision decision = returnRequest.getMovement().getCard().getDecision();

        String userLocation = "";
        if (user.getDistrict() != null) {
            userLocation = String.format("%s ", user.getDistrict().getName().getLat());
        } else if (user.getRegion() != null) {
            userLocation = String.format("%s ", user.getRegion().getName().getLat());
        }

        String returnRequestQr = generateQRCode(String.join("\n",
                String.valueOf(returnRequest.getId()),
                returnRequest.getCreatedTime().toString(),
                user.getFio(),
                String.valueOf(decision.getId()),
                String.format("%s%s", decision.getSeries(), decision.getNumber())
        ));

        LocalDateTime sendDateTime = returnRequest.getCreatedTime();
        String sendDate = String.format(
                "%s yil %s %s",
                sendDateTime.getYear(),
                sendDateTime.getDayOfMonth(),
                MonthConverter.valueOf(sendDateTime.getMonth().name()).getValue());
        String sendTime = sendDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        PdfReturnRequestDTO returnRequestDTO = new PdfReturnRequestDTO(returnRequest);

        returnRequestDTO.setRequestId(returnRequest.getId());
        returnRequestDTO.setSendDate(sendDate);
        returnRequestDTO.setSendTime(sendTime);
        returnRequestDTO.setReasonName(returnRequest.getReason().getDefaultName());
        returnRequestDTO.setComment(returnRequest.getComment());
        returnRequestDTO.setInspectorName(user.getFio());
        returnRequestDTO.setInspectorPosition(String.format("%s%s %s", userLocation, user.getOrgan().getShortName().getLat(), user.getPosition().getDefaultName()));

        returnRequestDTO.setOrganName(user.getOrgan().getDefaultName());
        returnRequestDTO.setOrganLogo(photoService.getUserOrganLogo(user));
        returnRequestDTO.setOrganAddress(organInfoService.getOrganInfo(user).getAddress());

        returnRequestDTO.setDecisionSeries(decision.getSeries());
        returnRequestDTO.setDecisionNumber(decision.getNumber());
        returnRequestDTO.setQr(returnRequestQr);

        return returnRequestDTO;
    }
}
