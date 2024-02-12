package uz.ciasev.ubdd_service.dto.internal.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.UzbAddress;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.Valid;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper=true)
public class JuridicCreateRequestDTO extends JuridicRequestDTO {

    @Valid
    @ValidAddress(message = ErrorCode.JURIDIC_FACT_ADDRESS_INVALID)
    @UzbAddress(message = ErrorCode.JURIDIC_FACT_ADDRESS_REGION_REQUIRED)
    private AddressRequestDTO factAddress;

    @Valid
    @ValidAddress(message = ErrorCode.JURIDIC_JUR_ADDRESS_INVALID)
    @UzbAddress(message = ErrorCode.JURIDIC_JUR_ADDRESS_REGION_REQUIRED)
    private AddressRequestDTO jurAddress;

    public Juridic buildJuridic() {
        Juridic juridic = super.buildJuridic();

        Optional.ofNullable(this.factAddress).map(AddressRequestDTO::buildAddress).ifPresent(juridic::setFactAddress);
        Optional.ofNullable(this.jurAddress).map(AddressRequestDTO::buildAddress).ifPresent(juridic::setJurAddress);

        return juridic;
    }
}
