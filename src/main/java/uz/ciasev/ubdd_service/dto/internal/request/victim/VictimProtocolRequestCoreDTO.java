package uz.ciasev.ubdd_service.dto.internal.request.victim;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class VictimProtocolRequestCoreDTO extends VictimRequestCoreDTO implements VictimProtocolRequestDTO {

    private String pinpp;

    @Valid
    private PersonRequestDTO person;

    @Valid
    private PersonDocumentRequestDTO document;

    @Valid
    @NotNull(message = ErrorCode.ACTUAL_ADDRESS_REQUIRED)
    @ValidAddress(message = ErrorCode.ACTUAL_ADDRESS_INVALID)
    private AddressRequestDTO actualAddress;

    @Valid
    @NotNull(message = ErrorCode.POST_ADDRESS_REQUIRED)
    @ValidAddress(message = ErrorCode.POST_ADDRESS_INVALID)
    private AddressRequestDTO postAddress;

    @Valid
    @NotNull(message = ErrorCode.VICTIM_DETAIL_REQUIRED)
    private VictimDetailRequestDTO victimDetail;
}
