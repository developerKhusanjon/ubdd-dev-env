package uz.ciasev.ubdd_service.entity.trans.gcp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.GcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractGcpTransEntity extends AbstractTransEntity {

    @Getter
    protected LocalDate createdTime;

    @Getter
    protected LocalDate editedTime;

    @Getter
    protected Long externalId;

    @Getter
    protected Boolean isActive = true;

    public void construct(GcpTransEntityCreateDTOI request) {
        this.createdTime = LocalDate.now();
        this.editedTime = null;
        this.externalId = request.getExternalId();
        this.isActive = true;
    }
}
