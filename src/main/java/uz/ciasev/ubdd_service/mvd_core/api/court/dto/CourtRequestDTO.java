package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CourtRequestDTO<T> {

    @Valid
    @NotNull(message = "SEND_DOCUMENT_REQUEST_REQUIRED")
    private T sendDocumentRequest;
    private SignatureDTO signature;

    public CourtRequestDTO(T sendDocumentRequest) {
        this.sendDocumentRequest = sendDocumentRequest;
        signature = null;
    }
}
