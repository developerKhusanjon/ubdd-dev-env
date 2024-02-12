package uz.ciasev.ubdd_service.entity.trans.gcp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.GcpTransPersonDocumentTypeCreateDTOI;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gcp_person_document_type")
@NoArgsConstructor
public class GcpTransPersonDocumentType extends AbstractSimpleGcpTransEntity<PersonDocumentType> {

    @Getter
    private Long orderId;

    public void construct(GcpTransPersonDocumentTypeCreateDTOI request, MultiLanguage name) {
        super.construct(request, name);
        this.orderId = request.getOrderId();
    }
}
