package uz.ciasev.ubdd_service.migration;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.OrganPunishmentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.ubdd.UbddInvoiceRequest;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPayeeInfoDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPayerInfoDTO;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.repository.dict.*;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartRepository;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleRepository;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleViolationTypeRepository;
import uz.ciasev.ubdd_service.repository.dict.person.OccupationRepository;
import uz.ciasev.ubdd_service.repository.dict.resolution.PunishmentTypeRepository;
import uz.ciasev.ubdd_service.repository.user.UserRepository;
import uz.ciasev.ubdd_service.service.execution.BillingExecutionService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.main.protocol.ProtocolCreateService;
import uz.ciasev.ubdd_service.service.main.resolution.UserAdmResolutionService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolDTOService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionDTOService;

import javax.validation.ConstraintViolation;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CsvProcessorService {

    private static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final UserRepository userRepository;
    private final ProtocolValidator protocolValidator;
    private final ProtocolDTOService protocolDTOService;
    private final ProtocolCreateService protocolCreateService;

    private final DecisionDTOService decisionDTOService;
    private final UserAdmResolutionService admResolutionService;

    private final InvoiceService invoiceService;
    private final BillingExecutionService billingExecutionService;

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final MtpRepository mtpRepository;
    private final ArticleRepository articleRepository;
    private final ArticlePartRepository articlePartRepository;
    private final ArticleViolationTypeRepository articleViolationTypeRepository;
    private final DepartmentRepository departmentRepository;
    private final OccupationRepository occupationRepository;
    private final PunishmentTypeRepository punishmentTypeRepository;

    public void startProcess(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        processCsv(resource.getFile().getPath());
    }

    private void processCsv(String filePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {

            CsvToBean<ProtocolData> csvToBean = new CsvToBeanBuilder<ProtocolData>(reader)
                    .withType(ProtocolData.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(',')
                    .build();

            for (ProtocolData row : csvToBean) {
                try {
                    saveToDatabase(row);
                } catch (Exception e) {
                    collectToListAndSaveSomeFile(e.getMessage() + "\n\n");
                }
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void collectToListAndSaveSomeFile(String errorMessage) {
        String resourcesFolderPath = "src/main/resources/errors";
        File resourcesFolder = new File(resourcesFolderPath);

        if (!resourcesFolder.exists()) {
            resourcesFolder.mkdirs();
        }

        String filePath = resourcesFolderPath + "/error_log.txt";
        File errorFile = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(errorFile, true))) {
            writer.write(errorMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Transactional
    private void saveToDatabase(ProtocolData protocolData) {
        User user = userRepository.findByUsernameIgnoreCase("ubdd-service").orElseThrow();

        Pair<String, String> protocolResult = saveProtocol(user, protocolData);
        if (!protocolResult.getFirst().equals("SUCCESS")) {
            throw new RuntimeException(protocolResult.getFirst() + " -> " + protocolResult.getSecond());
        }

        Pair<String, String> resolutionResult = saveResolution(user, protocolData);
        if (!resolutionResult.getFirst().equals("SUCCESS")) {
            throw new RuntimeException(resolutionResult.getFirst() + " -> " + resolutionResult.getSecond());
        }

        Pair<String, String> invoiceResult = saveInvoice(user, protocolData);
        if (!invoiceResult.getFirst().equals("SUCCESS")) {
            throw new RuntimeException(invoiceResult.getFirst() + " -> " + invoiceResult.getSecond());
        }

        Pair<String, String> paymentResult = savePayment(user, protocolData);
        if (!paymentResult.getFirst().equals("SUCCESS")) {
            throw new RuntimeException(paymentResult.getFirst() + " -> " + paymentResult.getSecond());
        }
    }

    private Pair<String, String> saveProtocol(User user, ProtocolData protocolData) {

        ProtocolRequestDTO protocolRequestDTO = new ProtocolRequestDTO();

        protocolRequestDTO.setExternalId(protocolData.getProtocol_externalId());
        protocolRequestDTO.setInspectorRegionId(protocolData.getProtocol_inspectorRegionId() == null ? null : Long.parseLong(protocolData.getProtocol_inspectorRegionId()));
        protocolRequestDTO.setInspectorDistrictId(protocolData.getProtocol_inspectorDistrictId() == null ? null : Long.parseLong(protocolData.getProtocol_inspectorDistrictId()));
        protocolRequestDTO.setInspectorPositionId(protocolData.getProtocol_inspectorPositionId() == null ? null : Long.parseLong(protocolData.getProtocol_inspectorPositionId()));
        protocolRequestDTO.setInspectorRankId(protocolData.getProtocol_inspectorRankId() == null ? null : Long.parseLong(protocolData.getProtocol_inspectorRankId()));
        protocolRequestDTO.setInspectorFio(protocolData.getProtocol_inspectorFio());
        protocolRequestDTO.setInspectorInfo(protocolData.getProtocol_inspectorInfo());
        protocolRequestDTO.setInspectorWorkCertificate(protocolData.getProtocol_inspectorWorkCertificate());
        protocolRequestDTO.setRegistrationTime(strToLocalDateTime(protocolData.getProtocol_registrationTime()));
        protocolRequestDTO.setViolationTime(strToLocalDateTime(protocolData.getProtocol_violationTime()));


        protocolRequestDTO.setArticle(buildArticleOrNull(protocolData.getProtocol_articleId()));
        protocolRequestDTO.setArticlePart(buildArticlePartOrNull(protocolData.getProtocol_articlePartId()));

        protocolRequestDTO.setFabula(protocolData.getProtocol_fabula());

        protocolRequestDTO.setRegion(buildRegionOrNull(protocolData.getProtocol_regionId()));
        protocolRequestDTO.setDistrict(buildDistrictOrNull(protocolData.getProtocol_districtId()));
        protocolRequestDTO.setMtp(buildMtpOrNull(protocolData.getProtocol_mtpId()));

        protocolRequestDTO.setAddress(protocolData.getMbprotocol_address());
        protocolRequestDTO.setIsFamiliarize(protocolData.getProtocol_isFamiliarize() == null ? null : Boolean.parseBoolean(protocolData.getProtocol_isFamiliarize()));
        protocolRequestDTO.setIsAgree(protocolData.getProtocol_isAgree() == null ? null : Boolean.parseBoolean(protocolData.getProtocol_isAgree()));

        protocolRequestDTO.setViolator(buildViolatorCreateRequestDTO(protocolData));

        try {
            protocolDTOService.buildDetailForCreateProtocol(user, () -> protocolCreateService.createElectronProtocol(user, protocolRequestDTO));
        } catch (Exception e) {
            String pro = protocolRequestDTO.getExternalId() + " CREATION PROTOCOL FAILED WITH: ";
            return Pair.of(pro, e.getMessage());
        }

        return Pair.of("SUCCESS", "SUCCESS");
    }

    private Pair<String, String> saveResolution(User user, ProtocolData protocolData) {

        SingleResolutionRequestDTO resolutionRequestDTO = new SingleResolutionRequestDTO();
        resolutionRequestDTO.setExternalId(Long.parseLong(protocolData.getResolution_externalId()));
        resolutionRequestDTO.setConsiderUserInfo(protocolData.getResolution_considerUserInfo());
        resolutionRequestDTO.setResolutionTime(strToLocalDateTime(protocolData.getResolution_resolutionTime()));
        resolutionRequestDTO.setIsArticle33(protocolData.getResolution_isArticle33() == null ? null : Boolean.parseBoolean(protocolData.getResolution_isArticle33()));
        resolutionRequestDTO.setIsArticle34(protocolData.getResolution_isArticle34() == null ? null : Boolean.parseBoolean(protocolData.getResolution_isArticle34()));
        resolutionRequestDTO.setDepartment(buildDepartmentOrNull(protocolData.getResolution_departmentId()));
        resolutionRequestDTO.setRegion(buildRegionOrNull(protocolData.getResolution_regionId()));
        resolutionRequestDTO.setDistrict(buildDistrictOrNull(protocolData.getResolution_districtId()));
        resolutionRequestDTO.setSignature(protocolData.getResolution_signature());
        resolutionRequestDTO.setDecisionType(DecisionTypeAlias.getInstanceById(protocolData.getResolution_decisionTypeId() == null ? null : Long.parseLong(protocolData.getResolution_decisionTypeId())));

        resolutionRequestDTO.setArticle(buildArticleOrNull(protocolData.getResolution_articleId()));
        resolutionRequestDTO.setArticlePart(buildArticlePartOrNull(protocolData.getResolution_articlePartId()));
        resolutionRequestDTO.setArticleViolationType(buildArticleViolationTypeOrNull(protocolData.getResolution_articleViolationTypeId()));
        resolutionRequestDTO.setExecutionFromDate(strToLocalDate(protocolData.getResolution_executionFromDate()));

        OrganPunishmentRequestDTO mainPunishment = new OrganPunishmentRequestDTO();
        mainPunishment.setPunishmentType(buildPunishmentTypeOrNull(protocolData.getResolution_mainPunishment_punishmentTypeId()));
        mainPunishment.setAmount(protocolData.getResolution_mainPunishment_amount() == null ? null : Long.parseLong(protocolData.getResolution_mainPunishment_amount()));

        mainPunishment.setIsDiscount70(protocolData.getInovice_isDiscount70() == null ? null : Boolean.parseBoolean(protocolData.getInovice_isDiscount70()));
        mainPunishment.setIsDiscount50(protocolData.getInovice_isDiscount50() == null ? null : Boolean.parseBoolean(protocolData.getInovice_isDiscount50()));

        mainPunishment.setDiscount70ForDate(strToLocalDate(protocolData.getInovice_discount70ForDate()));
        mainPunishment.setDiscount50ForDate(strToLocalDate(protocolData.getInovice_discount50ForDate()));

        mainPunishment.setDiscount70Amount(protocolData.getInovice_discount70Amount() == null ? null : (long) Double.parseDouble(protocolData.getInovice_discount70Amount()));
        mainPunishment.setDiscount50Amount(protocolData.getInovice_discount50Amount() == null ? null : (long) Double.parseDouble(protocolData.getInovice_discount50Amount()));


        resolutionRequestDTO.setMainPunishment(mainPunishment);


        try {
            decisionDTOService.buildListForCreate(() -> admResolutionService.createSingle(user, resolutionRequestDTO.getExternalId(), resolutionRequestDTO).getCreatedDecision());
        } catch (Exception e) {
            String pro = protocolData.getProtocol_externalId() + " CREATION RESOLUTION FAILED WITH: ";
            return Pair.of(pro, e.getMessage());
        }

        return Pair.of("SUCCESS", "SUCCESS");
    }


    private Pair<String, String> saveInvoice(User user, ProtocolData protocolData) {

        UbddInvoiceRequest invoiceRequest = new UbddInvoiceRequest();
        invoiceRequest.setIsDiscount70(protocolData.getInovice_isDiscount70() == null ? null : Boolean.parseBoolean(protocolData.getInovice_isDiscount70()));
        invoiceRequest.setIsDiscount50(protocolData.getInovice_isDiscount50() == null ? null : Boolean.parseBoolean(protocolData.getInovice_isDiscount50()));
        invoiceRequest.setExternalId(protocolData.getInovice_externalId() == null ? null : Long.parseLong(protocolData.getInovice_externalId()));
        invoiceRequest.setInvoiceId(protocolData.getInovice_invoiceId() == null ? null : Long.parseLong(protocolData.getInovice_invoiceId()));
        invoiceRequest.setInvoiceSerial(protocolData.getInovice_invoiceSerial());
        invoiceRequest.setInvoiceNumber(protocolData.getInovice_invoiceNumber());
        invoiceRequest.setInvoiceDate(strToLocalDate(protocolData.getInovice_invoiceDate()));
        invoiceRequest.setDiscount70ForDate(strToLocalDate(protocolData.getInovice_discount70ForDate()));
        invoiceRequest.setDiscount50ForDate(strToLocalDate(protocolData.getInovice_discount50ForDate()));
        invoiceRequest.setPenaltyPunishmentAmount(protocolData.getInovice_penaltyPunishmentAmount() == null ? null : (long) Double.parseDouble(protocolData.getInovice_penaltyPunishmentAmount()));
        invoiceRequest.setDiscount70Amount(protocolData.getInovice_discount70Amount() == null ? null : (long) Double.parseDouble(protocolData.getInovice_discount70Amount()));
        invoiceRequest.setDiscount50Amount(protocolData.getInovice_discount50Amount() == null ? null : (long) Double.parseDouble(protocolData.getInovice_discount50Amount()));
        invoiceRequest.setOrganName(protocolData.getInovice_organName());
        invoiceRequest.setBankInn(protocolData.getInovice_bankInn());
        invoiceRequest.setBankName(protocolData.getInovice_bankName());
        invoiceRequest.setBankCode(protocolData.getInovice_bankCode());
        invoiceRequest.setBankAccount(protocolData.getInovice_bankAccount());
        invoiceRequest.setPayerName(protocolData.getInovice_payerName());
        invoiceRequest.setPayerAddress(protocolData.getInovice_payerAddress());
        invoiceRequest.setPayerBirthdate(strToLocalDate(protocolData.getInovice_payerBirthdate()));

        try {
            invoiceService.create(user, invoiceRequest);
        } catch (Exception e) {
            String pro = protocolData.getProtocol_externalId() + " CREATION INVOICE FAILED WITH: ";
            return Pair.of(pro, e.getMessage());
        }

        return Pair.of("SUCCESS", "SUCCESS");
    }


    private Pair<String, String> savePayment(User user, ProtocolData protocolData) {

        BillingPaymentDTO paymentDTO = new BillingPaymentDTO();
        paymentDTO.setId(protocolData.getPayments_id() == null ? null : Long.parseLong(protocolData.getPayments_id()));
        paymentDTO.setExternalId(protocolData.getPayments_externalId() == null ? null : Long.parseLong(protocolData.getPayments_externalId()));
        paymentDTO.setInvoiceSerial(protocolData.getPayments_invoiceSerial());
        paymentDTO.setBid(protocolData.getPayments_bid());
        paymentDTO.setAmount(protocolData.getPayments_amount() == null ? null : Double.parseDouble(protocolData.getPayments_amount()));
        paymentDTO.setDocNumber(protocolData.getPayments_docNumber());
        paymentDTO.setPaidAt(strToLocalDateTime(protocolData.getPayments_paidAt()));

        BillingPayerInfoDTO payerInfoDTO = new BillingPayerInfoDTO();
        payerInfoDTO.setFromBankCode("0000");
        payerInfoDTO.setFromBankAccount("0000");
        payerInfoDTO.setFromBankName("Bank nomi ko'rsatilmagan");
        payerInfoDTO.setFromInn("0000");
        paymentDTO.setPayerInfo(payerInfoDTO);

        BillingPayeeInfoDTO payeeInfoDTO = new BillingPayeeInfoDTO();
        payeeInfoDTO.setToBankCode("0000");
        payeeInfoDTO.setToBankAccount("0000");
        payeeInfoDTO.setToBankName("Bank nomi ko'rsatilmagan");
        payeeInfoDTO.setToInn("0000");
        paymentDTO.setPayeeInfo(payeeInfoDTO);

        try {
            billingExecutionService.handlePayment(user, paymentDTO);
        } catch (Exception e) {
            String pro = protocolData.getProtocol_externalId() + " CREATION PAYMENT FAILED WITH: ";
            return Pair.of(pro, e.getMessage());
        }

        return Pair.of("SUCCESS", "SUCCESS");
    }

    private LocalDateTime strToLocalDateTime(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return LocalDateTime.parse(value, localDateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Failed to parse date-time: " + value, e);
        }
    }

    private LocalDate strToLocalDate(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            String datePart = value.split(" ")[0];
            return LocalDate.parse(datePart, localDateFormatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Failed to parse date-time: " + value, e);
        }
    }

    private ViolatorCreateRequestDTO buildViolatorCreateRequestDTO(ProtocolData protocolData) {
        ViolatorCreateRequestDTO violator = new ViolatorCreateRequestDTO();
        violator.setPinpp(protocolData.getProtocol_violator_pinpp());
        violator.setMobile(protocolData.getProtocol_violator_mobile());
        violator.setLandline(protocolData.getProtocol_violator_landline());


        AddressRequestDTO actualAddress = new AddressRequestDTO();
        actualAddress.setCountry(buildCountryOrNull(protocolData.getProtocol_violator_actualAddress_countryId()));
        actualAddress.setRegion(buildRegionOrNull(protocolData.getProtocol_violator_actualAddress_regionId()));
        actualAddress.setDistrict(buildDistrictOrNull(protocolData.getProtocol_violator_actualAddress_districtId()));
        actualAddress.setAddress(protocolData.getProtocol_violator_actualAddress_address());
        violator.setActualAddress(actualAddress);

        AddressRequestDTO postAddress = new AddressRequestDTO();
        postAddress.setCountry(buildCountryOrNull(protocolData.getProtocol_violator_postAddress_countryId()));
        postAddress.setRegion(buildRegionOrNull(protocolData.getProtocol_violator_postAddress_regionId()));
        postAddress.setDistrict(buildDistrictOrNull(protocolData.getProtocol_violator_postAddress_districtId()));
        postAddress.setAddress(protocolData.getProtocol_violator_postAddress_address());
        violator.setPostAddress(postAddress);

        ViolatorDetailRequestDTO violatorDetail = new ViolatorDetailRequestDTO();
        violatorDetail.setOccupation(buildOccupationOrNull(protocolData.getProtocol_violator_violatorDetail_occupationId()));
        violatorDetail.setEmploymentPlace(protocolData.getProtocol_violator_violatorDetail_employmentPlace());
        violatorDetail.setEmploymentPosition(protocolData.getProtocol_violator_violatorDetail_employmentPosition());
        violatorDetail.setAdditionally(protocolData.getProtocol_violator_violatorDetail_additionally());
        violatorDetail.setSignature(protocolData.getProtocol_violator_violatorDetail_signature());
        violator.setViolatorDetail(violatorDetail);

        return violator;
    }

    private Country buildCountryOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return countryRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(Country.class, Long.parseLong(id))
        );
    }

    private Region buildRegionOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return regionRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(Region.class, Long.parseLong(id))
        );
    }

    private District buildDistrictOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return districtRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(District.class, Long.parseLong(id))
        );
    }

    private Mtp buildMtpOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return mtpRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(Mtp.class, Long.parseLong(id))
        );
    }

    private Department buildDepartmentOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return departmentRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(Department.class, Long.parseLong(id))
        );
    }

    private Article buildArticleOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return articleRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(Article.class, Long.parseLong(id))
        );
    }

    private ArticlePart buildArticlePartOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return articlePartRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(ArticlePart.class, Long.parseLong(id))
        );
    }

    private ArticleViolationType buildArticleViolationTypeOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return articleViolationTypeRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(ArticleViolationType.class, Long.parseLong(id))
        );
    }

    private Occupation buildOccupationOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return occupationRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(Occupation.class, Long.parseLong(id))
        );
    }

    private PunishmentType buildPunishmentTypeOrNull(String id) {
        if (id == null || id.isBlank()) return null;
        return punishmentTypeRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new EntityByIdNotFound(PunishmentType.class, Long.parseLong(id))
        );
    }

}

