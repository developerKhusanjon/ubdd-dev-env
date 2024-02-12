package uz.ciasev.ubdd_service.dto.internal.trans.response.gcp;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransPersonDocumentType;

@Getter
public class GcpTransPersonDocumentTypeResponseDTO extends SimpleGcpTransEntityResponseDTO {

    private final Long orderId;

    public GcpTransPersonDocumentTypeResponseDTO(GcpTransPersonDocumentType entity) {
        super(entity);
        this.orderId = entity.getOrderId();
    }
}
