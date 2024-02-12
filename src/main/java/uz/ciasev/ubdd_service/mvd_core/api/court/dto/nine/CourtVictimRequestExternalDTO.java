package uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class CourtVictimRequestExternalDTO implements CourtBaseDTO {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long caseId;

    @NotNull(message = ErrorCode.CLAIM_ID_REQUIRED)
    private Long claimId;

    private String pinpp;

    @Valid
    private PersonRequestExternalDTO person;

    @Valid
    private PersonDocumentRequestExternalDTO document;

    public PersonDocumentRequestExternalDTO getDocument() {
        if (document == null) return new PersonDocumentRequestExternalDTO();
        return document;
    }

    @Valid
    private AddressRequestExternalDTO actualAddress;

    @Valid
    private AddressRequestExternalDTO postAddress;

    @Valid
    private VictimDetailRequestExternalDTO victimDetail;

    private String mobile;

    private String landline;

    private String inn;

}
