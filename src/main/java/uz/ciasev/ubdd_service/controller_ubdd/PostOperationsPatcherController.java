package uz.ciasev.ubdd_service.controller_ubdd;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ArrayStack;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ciasev.ubdd_service.config.security.CurrentUser;
import uz.ciasev.ubdd_service.dto.FailedRow;
import uz.ciasev.ubdd_service.dto.PostOperationPatchDto;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestAdditionalDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolStatisticDataRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.CancellationResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.ubdd.UbddInvoiceRequest;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReportType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPayeeInfoDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPayerInfoDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.service.excel.ExcelService;
import uz.ciasev.ubdd_service.service.execution.BillingExecutionService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.main.admcase.AdmCaseActionService;
import uz.ciasev.ubdd_service.service.main.protocol.ProtocolCreateService;
import uz.ciasev.ubdd_service.service.main.protocol.ProtocolMainService;
import uz.ciasev.ubdd_service.service.main.resolution.UserAdmResolutionService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolDTOService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionActionService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionDTOService;
import uz.ciasev.ubdd_service.utils.ContentType;
import uz.ciasev.ubdd_service.utils.ControllerUtils;
import uz.ciasev.ubdd_service.utils.validator.FileContentRequired;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Profile({"local", "test"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${mvd-ciasev.url-v0}/migrate", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PostOperationsPatcherController {
    private final ProtocolCreateService protocolCreateService;
    private final ProtocolDTOService dtoService;
    private final ProtocolMainService protocolMainService;
    private final ProtocolDTOService protocolDTOServiceNew;
    private final ProtocolService protocolService;
    private final AdmCaseActionService admCaseActionService;

    private final ResolutionActionService resolutionActionService;
    private final DecisionDTOService decisionDTOService;
    private final UserAdmResolutionService admResolutionService;

    private final InvoiceService invoiceService;
    private final BillingExecutionService billingExecutionService;

    private final ProtocolRepository protocolRepository;

    private final ExcelService excelService;

    @SneakyThrows
    @PostMapping("/")
    public ResponseEntity<byte[]> doPatch(@CurrentUser User user, @RequestParam("file") @Valid @FileContentRequired MultipartFile file) {
        List<FailedRow> failedRows = validateAndMakeDto(file).stream().map(data -> {
                    FailedRow failedRow = null;
                    try {
                        sequenceOperations(user, data.getFirst());
                    } catch (Exception e) {
                        failedRow = new FailedRow(data.getSecond());
                        log.error(e.getMessage());
                    }

                    return failedRow;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        return ControllerUtils.buildFileResponse(excelService.saveToExcel(FailedRow.class, failedRows.stream()), ContentType.EXCEL, "ubdd_failed_rows.xlsx");
//        return null;
    }

    @Transactional
    private void sequenceOperations(User user, PostOperationPatchDto postOperation) {
        dtoService.buildDetailForCreateProtocol(user, () -> protocolCreateService.createElectronProtocol(user, postOperation.protocol));
        protocolMainService.editProtocolQualification(user, postOperation.protocolId, postOperation.qualification);
//        admCaseActionService.mergeAdmCases(user, fromAdmCaseId, toAdmCaseId);
        protocolDTOServiceNew.buildDetail(user, () -> protocolService.findById(postOperation.protocolId));
        decisionDTOService.buildListForCreate(() -> admResolutionService.createSingle(user, postOperation.singleResolution.getExternalId(), postOperation.singleResolution).getCreatedDecision());
//        resolutionActionService.cancelResolutionByOrgan(user, admCaseId, dto);
        invoiceService.create(user, postOperation.ubddInvoice);
        billingExecutionService.handlePayment(user, postOperation.billingPayment);
    }

    private List<Pair<PostOperationPatchDto, Map<String, String>>> validateAndMakeDto(MultipartFile file) throws IOException, CsvException {
        List<Pair<PostOperationPatchDto, Map<String, String>>> fetchedData = new ArrayList<>();

        Reader reader = new InputStreamReader(file.getInputStream());

        // Parse CSV data
        try (CSVReader csvReader = new CSVReaderBuilder(reader).build()) {
            List<String[]> rows = csvReader.readAll();

            // 1-row contains keys rest
            String[] keys = rows.get(0);
            for (int i = 1; i < rows.size(); i++) {
                Map<String, String> vls = new HashMap<>();
                for (int j = 0; j < keys.length; j++) {
                    vls.put(keys[j], rows.get(i)[j]);
                }

                PostOperationPatchDto postOperationPatchDto = new PostOperationPatchDto();

                //TODO: Fill protocol
                Protocol protocol = new Protocol();

                QualificationRequestDTO qualification = new QualificationRequestDTO();
                Article article = new Article();
                ArticlePart articlePart = new ArticlePart();
                ArticleViolationType articleViolationType = new ArticleViolationType();
//                JuridicCreateRequestDTO juridicCreateDto = new JuridicCreateRequestDTO();

//            AddressRequestDTO factAddress = new AddressRequestDTO();
//            factAddress.setAddress();
//            factAddress.setDistrict();
//            factAddress.setCountry();
//            factAddress.setRegion();
//            AddressRequestDTO jurAddress = new AddressRequestDTO();
//            jurAddress.setAddress();
//            jurAddress.setDistrict();
//            jurAddress.setCountry();
//            jurAddress.setRegion();
//
//            juridicCreateDto.setFactAddress(factAddress);
//            juridicCreateDto.setJurAddress(jurAddress);

                article.setId(vls.get("protocol_articleId").isBlank() ? -1L : Long.parseLong(vls.get("protocol_articleId")));
                articlePart.setId(vls.get("protocol_articlePartId").isBlank() ? -1L : Long.parseLong(vls.get("protocol_articlePartId")));
                articleViolationType.setId(vls.get("resolution_articleViolationTypeId").isBlank() ? -1L : Long.parseLong(vls.get("resolution_articleViolationTypeId")));

                qualification.setFabula(vls.getOrDefault("protocol_fabula", null));
                qualification.setArticle(article.getId() == -1 ? null : article);
                qualification.setArticlePart(articlePart.getId() == -1 ? null : articlePart);
                //TODO: additional articles?
//            qualification.setAdditionArticles();
                qualification.setArticleViolationType(articleViolationType.getId() == -1 ? null : articleViolationType);
//                qualification.setJuridic(juridicCreateDto);
                //TODO: repeatableProtocolIds?
//            qualification.setRepeatabilityProtocolsId();

                ProtocolRequestDTO protocolRequest = new ProtocolRequestDTO();
//                protocolRequest.setJuridic(juridicCreateDto);
                protocolRequest.setDistrict(new District(Long.valueOf(vls.get("protocol_districtId"))));
//                ProtocolRequestAdditionalDTO protocolRequestAdditional = new ProtocolRequestAdditionalDTO();
//            ProtocolStatisticDataRequestDTO protocolStatisticDataRequest = new ProtocolStatisticDataRequestDTO();
//            StatisticReportType statisticReportType = new StatisticReportType();
//            protocolStatisticDataRequest.setReportType(statisticReportType);
//            protocolRequestAdditional.setStatistic(protocolStatisticDataRequest);
//
//            protocolRequestAdditional.setUbdd();
//            protocolRequestAdditional.setTransport();
//            protocolRequest.setAdditional(protocolRequestAdditional);
                protocolRequest.setAddress(vls.get("mbprotocol_address"));
//            protocolRequest.setAudioUri();
                protocolRequest.setInspectorFio(vls.get("protocol_inspectorFio"));
                protocolRequest.setInspectorInfo(vls.get("protocol_inspectorInfo"));
//            protocolRequest.setInspectorSignature();
                protocolRequest.setInspectorWorkCertificate(vls.get("protocol_inspectorWorkCertificate"));
                protocolRequest.setInspectorPositionId(vls.get("protocol_inspectorPositionId").isBlank() ? null : Long.valueOf(vls.get("protocol_inspectorPositionId")));
                protocolRequest.setInspectorRankId(vls.get("protocol_inspectorRankId").isBlank() ? null : Long.valueOf(vls.get("protocol_inspectorRankId")));
                protocolRequest.setInspectorRegionId(vls.get("protocol_inspectorRegionId").isBlank() ? null : Long.valueOf(vls.get("protocol_inspectorRegionId")));
                protocolRequest.setInspectorDistrictId(vls.get("protocol_inspectorDistrictId").isBlank() ? null : Long.valueOf(vls.get("protocol_inspectorDistrictId")));
                protocolRequest.setIsFamiliarize(!vls.get("protocol_isFamiliarize").isBlank() && Boolean.parseBoolean(vls.getOrDefault("protocol_isFamiliarize", "false").toLowerCase()));
//            protocolRequest.setIsTablet(Boolean.valueOf(vls.get("").toLowerCase()));
                protocolRequest.setIsAgree(vls.get("protocol_isAgree").trim().isBlank() && Boolean.parseBoolean(vls.getOrDefault("protocol_isAgree", "false").toLowerCase()));
                Mtp mtp = new Mtp();
                String mtpId = vls.get("protocol_mtpId");
                mtp.setId(mtpId == null || mtpId.isEmpty() ? null : Long.valueOf(mtpId));
                protocolRequest.setMtp(mtp);

                protocolRequest.setRegion(vls.get("protocol_regionId").isBlank() ? null : new Region(Long.valueOf(vls.get("protocol_regionId"))));
//            protocolRequest.setExplanatoryText();
//            protocolRequest.setGovernmentDamageAmount();
//            protocolRequest.setLatitude();
//            protocolRequest.setLongitude();
                String registrationTime = vls.get("protocol_registrationTime");
                protocolRequest.setRegistrationTime(registrationTime == null || registrationTime.isBlank() ? null : LocalDateTime.parse(registrationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            protocolRequest.setVideoUri();
//            protocolRequest.setUbddDataBind();
//            protocolRequest.setUbddGroup();
//            protocolRequest.setVehicleNumber();
                protocolRequest.setViolationTime(LocalDateTime.parse(vls.get("protocol_violationTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                ViolatorCreateRequestDTO violatorCreateRequest = new ViolatorCreateRequestDTO();
                violatorCreateRequest.setPinpp(vls.get("protocol_violator_pinpp"));

                protocolRequest.setViolator(violatorCreateRequest);
                protocolRequest.setExternalId(vls.get("protocol_externalId"));

                UbddInvoiceRequest ubddInvoiceRequest = new UbddInvoiceRequest();
                ubddInvoiceRequest.setBankAccount(vls.get("inovice_bankAccount"));
                ubddInvoiceRequest.setBankCode(vls.get("inovice_bankCode"));
                ubddInvoiceRequest.setBankInn(vls.get("inovice_bankInn"));
                ubddInvoiceRequest.setBankName(vls.get("inovice_bankName"));
                ubddInvoiceRequest.setInvoiceId(vls.get("inovice_invoiceId").trim().isBlank() ? null : Long.valueOf(vls.get("inovice_invoiceId")));
                ubddInvoiceRequest.setInvoiceSerial(vls.get("inovice_invoiceSerial"));
                ubddInvoiceRequest.setInvoiceNumber(vls.get("inovice_invoiceNumber"));
                ubddInvoiceRequest.setInvoiceDate(LocalDate.parse(vls.get("inovice_invoiceDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                ubddInvoiceRequest.setDiscount50Amount(vls.get("inovice_discount50Amount").trim().isBlank() ? null : Long.valueOf(vls.get("inovice_discount50Amount")));
                ubddInvoiceRequest.setDiscount50ForDate(LocalDate.parse(vls.get("inovice_discount50ForDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                ubddInvoiceRequest.setDiscount70Amount(vls.get("inovice_discount70Amount").trim().isBlank() ? null : Long.valueOf(vls.get("inovice_discount70Amount")));
                ubddInvoiceRequest.setDiscount70ForDate(LocalDate.parse(vls.get("inovice_discount70ForDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                ubddInvoiceRequest.setIsDiscount50(vls.get("inovice_isDiscount50").trim().isBlank() && Boolean.parseBoolean(vls.getOrDefault("inovice_isDiscount50", "false").toLowerCase()));
                ubddInvoiceRequest.setIsDiscount70(vls.get("inovice_isDiscount70").trim().isBlank() && Boolean.parseBoolean(vls.getOrDefault("inovice_isDiscount70", "false").toLowerCase()));
                ubddInvoiceRequest.setExternalId(vls.get("inovice_externalId").trim().isBlank() ? null : Long.valueOf(vls.get("inovice_externalId")));
                ubddInvoiceRequest.setOrganName(vls.get("inovice_organName"));
//            ubddInvoiceRequest.setOwnerTypeId(vls.get());
                ubddInvoiceRequest.setPayerAddress(vls.get("inovice_payerAddress"));
                ubddInvoiceRequest.setPayerBirthdate(LocalDate.parse(vls.get("inovice_payerBirthdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                ubddInvoiceRequest.setPayerName(vls.get("inovice_payerName"));

                ubddInvoiceRequest.setPenaltyPunishmentAmount(vls.get("inovice_penaltyPunishmentAmount").trim().isBlank() ? null : Long.valueOf(vls.get("inovice_penaltyPunishmentAmount")));
//            ubddInvoiceRequest.setPenaltyPunishmentId();

                BillingPaymentDTO billingPayment = new BillingPaymentDTO();
                billingPayment.setId(vls.get("payments_id").trim().isBlank() ? null : Long.valueOf(vls.get("payments_id")));
                billingPayment.setExternalId(vls.get("payments_externalId").trim().isBlank() ? null : Long.valueOf(vls.get("payments_externalId")));
                billingPayment.setInvoiceSerial(vls.get("payments_invoiceSerial"));
                billingPayment.setBid(vls.get("payments_bid"));
                billingPayment.setAmount(vls.get("payments_amount").trim().isBlank() ? null : Double.valueOf(vls.get("payments_amount")));
//            billingPayment.setAdmCaseId();
                billingPayment.setDocNumber(vls.get("payments_docNumber"));
                String paidAt = vls.get("payments_paidAt");
                billingPayment.setPaidAt(paidAt == null || paidAt.isBlank() ? null : LocalDateTime.parse(paidAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                BillingPayeeInfoDTO billingPayeeInfo = new BillingPayeeInfoDTO();
                billingPayeeInfo.setToBankAccount(vls.get("payments_payeeInfo_toBankAccount"));
                billingPayeeInfo.setToInn(vls.get("payments_payeeInfo_toInn"));
                billingPayeeInfo.setToBankCode(vls.get("payments_payeeInfo_toBankCode"));
                billingPayeeInfo.setToBankName(vls.get("payments_payeeInfo_toBankName"));
                billingPayment.setPayeeInfo(billingPayeeInfo);
                BillingPayerInfoDTO billingPayerInfo = new BillingPayerInfoDTO();
                billingPayerInfo.setFromBankAccount(vls.get("payments_payerInfo_fromBankAccount"));
                billingPayerInfo.setFromInn(vls.get("payments_payerInfo_fromInn"));
                billingPayerInfo.setFromBankCode(vls.get("payments_payerInfo_fromBankCode"));
                billingPayerInfo.setFromBankName(vls.get("payments_payerInfo_fromBankName"));
                billingPayment.setPayerInfo(billingPayerInfo);

//            CancellationResolutionRequestDTO cancellationResolutionRequest = new CancellationResolutionRequestDTO();
//            cancellationResolutionRequest.setCancellationTime(vls.get(""));
//            cancellationResolutionRequest.setOrganCancellation();
//            cancellationResolutionRequest.setReasonCancellation();

                SingleResolutionRequestDTO singleResolutionRequest = new SingleResolutionRequestDTO();
//            singleResolutionRequest.setViolatorId();
                singleResolutionRequest.setExternalId(vls.get("resolution_externalId").trim().isBlank() ? null : Long.valueOf(vls.get("resolution_externalId")));
                singleResolutionRequest.setDistrict(vls.get("resolution_districtId").trim().isBlank() ? null : new District(Long.valueOf(vls.get("resolution_districtId"))));
                ArticlePart resolutionArticlePart = new ArticlePart();
                resolutionArticlePart.setId(vls.get("resolution_articlePartId").trim().isBlank() ? null : Long.valueOf(vls.get("resolution_articlePartId")));
                singleResolutionRequest.setArticlePart(resolutionArticlePart);
                singleResolutionRequest.setRegion(vls.get("resolution_regionId").trim().isBlank() ? null : new Region(Long.valueOf(vls.get("resolution_regionId"))));
                ArticleViolationType resoArticleViolationType = new ArticleViolationType();
                resoArticleViolationType.setId(vls.get("resolution_articleViolationTypeId").trim().isBlank() ? null : Long.valueOf(vls.get("resolution_articleViolationTypeId")));
                singleResolutionRequest.setArticleViolationType(resoArticleViolationType);

                postOperationPatchDto.protocolId = protocol.getId();
                postOperationPatchDto.qualification = qualification;
                postOperationPatchDto.protocol = protocolRequest;
                postOperationPatchDto.ubddInvoice = ubddInvoiceRequest;
                postOperationPatchDto.billingPayment = billingPayment;
//            postOperationPatchDto.cancellationResolution =
                postOperationPatchDto.singleResolution = singleResolutionRequest;

                fetchedData.add(Pair.of(postOperationPatchDto, vls));
            }
        } catch (Exception e) {
            log.error("Error while importing data from csv file: ", e.getMessage());
            throw e;
        }

        return fetchedData;
    }
}
