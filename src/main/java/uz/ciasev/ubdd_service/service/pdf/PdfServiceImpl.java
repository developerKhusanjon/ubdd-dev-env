package uz.ciasev.ubdd_service.service.pdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.mib.PdfReturnRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.payment.PdfPaymentWrapperDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.requirement.PdfRequirementDTO;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticlesProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolRequirementProjection;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.exception.ExternalResolutionFileAbsentException;
import uz.ciasev.ubdd_service.exception.PdfServiceException;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;
import uz.ciasev.ubdd_service.entity.document.RequirementGeneration;
import uz.ciasev.ubdd_service.service.file.FileService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;

import java.util.List;
import java.util.function.Supplier;

import static uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias.PUNISHMENT;
import static uz.ciasev.ubdd_service.service.pdf.Template.*;

@Slf4j
@Service
public class PdfServiceImpl implements PdfService {

    private final String host;

    private final RestTemplate restTemplate;

    private final FileService fileService;

    private final DecisionService decisionService;

    private final PdfModelBuilderService templateBuilderService;

    public PdfServiceImpl(@Value("${pdf-api.host}") String host,
                          @Qualifier("pdfRestTemplate") RestTemplate restTemplate,
                          FileService fileService,
                          DecisionService decisionService,
                          PdfModelBuilderService templateBuilderService) {
        this.host = host;
        this.restTemplate = restTemplate;
        this.fileService = fileService;
        this.decisionService = decisionService;
        this.templateBuilderService = templateBuilderService;
    }

    /**
     * Данный метод возвращает Карор или Прекращение
     */
    @Override
    public PdfFile getDecision(Long decisionId, DocumentGenerationLog documentGenerationLog) {

        Decision decision = decisionService.getById(decisionId);

        if (decision.getIsSavedPdf()) {
            return getSavedPdfDecision(decision);
        }


        PdfModel model;
        Template template;
        if (decision.getDecisionTypeAlias().is(PUNISHMENT)) {
            template = KAROR_AND_MAIL;
            model = templateBuilderService.buildPenaltyDecisionModel(decision, documentGenerationLog);
        } else {
            template = TERMINATION_DECISION;
            model = templateBuilderService.buildTerminationDecisionModel(decision, documentGenerationLog);
        }

        byte[] content = sendRequest(template, model);

        return PdfFile.of(model, content);
    }


    private PdfFile getSavedPdfDecision(Decision decision) {

        Resolution resolution = decision.getResolution();

        if (resolution.getCourtDecisionUri() == null) {
            throw new ExternalResolutionFileAbsentException();
        }

        byte[] content = fileService
                .getOrThrow(resolution.getCourtDecisionUri());

        return PdfFile.of(new PdfModel(decision), content);
    }

    /**
     * Данный метод возвращает Квитанцию для суда
     */
    @Override
    public PdfFile getInvoiceForCourt(Long invoiceId) {

        var model = templateBuilderService.buildInvoiceModel(invoiceId);

        byte[] content = sendRequest(INVOICE_FOR_COURT, model);

        return PdfFile.of(model, content);
    }

    @Override
    public PdfFile getProtocol(Long protocolId) {

        var model = templateBuilderService.buildProtocolModel(protocolId);

        byte[] content = sendRequest(PROTOCOL, model);

        return PdfFile.of(model, content);
    }

    @Override
    public PdfFile getDecisionMail(Decision decision, OrganInfo organInfo, String number) {

        var model = templateBuilderService.buildMailModel(decision, organInfo, number);

        byte[] content = sendRequest(KAROR_AND_MAIL, model);

        return PdfFile.of(model, content);
    }

    @Override
    public byte[] getDecisionMailContent(Decision decision, OrganInfo organInfo, String number) {

        return getDecisionMail(decision, organInfo, number).getContent();
    }

    @Override
    public PdfFile getMibPresentMail(Decision decision, OrganInfo organInfo, String number) {

        var model = templateBuilderService.buildMailModel(decision, organInfo, number);
        model.getPost().setIsWarning(true);

        byte[] content = sendRequest(KAROR_AND_MAIL, model);

        return PdfFile.of(model, content);
    }

    @Override
    public byte[] getMibPresentMailContent(Decision decision, OrganInfo organInfo, String number) {

        return getMibPresentMail(decision, organInfo, number).getContent();
    }

    @Override
    public PdfFile getPersonCard(Long violatorDetailId) {

        var model = templateBuilderService.buildPersonCardModel(violatorDetailId);

        byte[] content = sendRequest(PERSON_CARD_FOR_COURT, model);

        return PdfFile.of(model, content);
    }

    @Override
    public PdfFile getPayments(Long invoiceId) {

        var model = templateBuilderService.buildPaymentModel(invoiceId);

        byte[] content = sendRequest(PAYMENTS, model);

        return PdfFile.of(model, content);
    }

    @Override
    public PdfFile getPenaltyPayments(Long violatorId) {

        PdfPaymentWrapperDTO model = templateBuilderService.buildViolatorPaymentModel(violatorId, InvoiceOwnerTypeAlias.PENALTY);

        byte[] content = sendRequest(VIOLATOR_PAYMENTS, model);

        return PdfFile.of(model, content);
    }

    @Override
    public PdfFile getCompensationPayments(Long violatorId) {

        PdfPaymentWrapperDTO model = templateBuilderService.buildViolatorPaymentModel(violatorId, InvoiceOwnerTypeAlias.COMPENSATION);

        byte[] content = sendRequest(VIOLATOR_PAYMENTS, model);

        return PdfFile.of(model, content);
    }

    @Override
    public PdfFile getSms(Long smsNotificationId) {

        var model = templateBuilderService.buildSmsModel(smsNotificationId);

        byte[] content = sendRequest(SMS, model);

        return PdfFile.of(model, content);
    }

    /**
     * Данный метод возвращает Печать требования
     */
    @Override
    public PdfFile getRequirement(RequirementGeneration registration, Supplier<List<ProtocolRequirementProjection>> protocolsSupplier, Supplier<List<ProtocolArticlesProjection>> articlesSupplier) {

        PdfRequirementDTO model = templateBuilderService.buildRequirementModel(registration, protocolsSupplier, articlesSupplier);

        byte[] content = sendRequest(Template.REQUIREMENT, model);

        return PdfFile.of(model, content);
    }

    @Override
    public PdfFile getMibReturnRequest(Long returnRequestId) {

        PdfReturnRequestDTO model = templateBuilderService.buildMibReturnRequestModel(returnRequestId);

        byte[] content = sendRequest(Template.RETURN_REQUEST, model);

        return PdfFile.of(model, content);
    }


    /**
     * Отправляем запрос с типом запрашиваемого PDF и моделью данных
     */
    private byte[] sendRequest(Template path, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        HttpEntity<JsonNode> requestBody = new HttpEntity<>(objectMapper.convertValue(body, JsonNode.class), headers);
        HttpEntity<Object> requestBody = new HttpEntity<>(body, headers);

        try {
            return restTemplate.postForObject(host + "/" + path.getValue(), requestBody, byte[].class);
        } catch (RestClientException e) {
            log.error("PdfApi error response: '{}'", e.getLocalizedMessage(), e);
            throw new PdfServiceException();
        }
    }
}