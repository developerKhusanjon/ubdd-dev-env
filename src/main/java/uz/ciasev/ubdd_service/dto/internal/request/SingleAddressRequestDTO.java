package uz.ciasev.ubdd_service.dto.internal.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

@Data
@EqualsAndHashCode(callSuper=true)
@ValidAddress(message = ErrorCode.INVALID_ADDRESS)
public class SingleAddressRequestDTO extends AddressRequestDTO {}
