package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.GcpTransPersonDocumentTypeCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransPersonDocumentType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class GcpTransPersonDocumentTypeCreateRequestDTO extends GcpTransEntityCreateRequestDTO
        implements GcpTransPersonDocumentTypeCreateDTOI, TransEntityCreateRequest<GcpTransPersonDocumentType> {

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private PersonDocumentType internal;

    @NotNull(message = ErrorCode.ORDER_ID_REQUIRED)
    @Min(value = 100, message = ErrorCode.ORDER_ID_MIN_MAX_SIZE)
    @Max(value = 999, message = ErrorCode.ORDER_ID_MIN_MAX_SIZE)
    private Long orderId;

    @Override
    public void applyToNew(GcpTransPersonDocumentType entity) {
        entity.construct(this, internal.getName());
    }
}
