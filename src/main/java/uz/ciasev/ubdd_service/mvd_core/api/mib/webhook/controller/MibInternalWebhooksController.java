package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibPayedAmountRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.execution.MibApiExecutionService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.protocol.MibApiAdmService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmCaseCreatedResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmTerminationDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MvdExecutionResponseDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.utils.validator.FileContentRequired;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "${mvd-ciasev.webhooks.base-url}/mib-internal", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Profile("ubdd-api")
public class MibInternalWebhooksController {

    private final MibApiExecutionService executionService;
    private final MibApiAdmService admService;

    @PostMapping("/execution")
    public MvdExecutionResponseDTO execution(@RequestBody @Valid MibRequestDTO result) throws IOException {

        if (Objects.nonNull(result.getEnvelopeId()) && Objects.nonNull(result.getOffenseId())) {
            throw new ValidationException(ErrorCode.MIB_DOCUMENT_ID_AMBIGUOUS);
        }

        if (Objects.nonNull(result.getEnvelopeId())) {
            return executionService.executionResultWebhook(result.getEnvelopeId(), result);
        }
        if (Objects.nonNull(result.getOffenseId())) {
            admService.execution(result.getOffenseId(), result);
            return new MvdExecutionResponseDTO();
        }

        throw new ValidationException(ErrorCode.MIB_DOCUMENT_ID_EMPTY);
    }

    @PostMapping("/payed-amount")
    public MvdExecutionResponseDTO execution(@RequestBody MibPayedAmountRequestDTO result) throws IOException {

        if (Objects.nonNull(result.getEnvelopeId())) {
            return executionService.getPayedAmount(result.getEnvelopeId(), result);
        }

        throw new ValidationException(ErrorCode.MIB_DOCUMENT_ID_EMPTY);
    }

    @PostMapping("/adm-case")
    public MibAdmCaseCreatedResponseDTO createAdmCase(@RequestBody @Valid MibAdmCaseRequestDTO requestDTO) {
        return admService.createAdmCase(requestDTO);
    }

    @PostMapping("/terminate")
    public void terminateResolution(@RequestBody @Valid MibAdmTerminationDTO dto) {
        admService.terminateResolution(dto);
    }

    @PostMapping("/resolution/{id}/pdf")
    public void decisionPdf(@PathVariable(name = "id") Long decisionId,
                            @RequestParam("file") @Valid @FileContentRequired MultipartFile file) throws IOException {
        admService.decisionPdf(decisionId, file.getBytes());
    }
}
