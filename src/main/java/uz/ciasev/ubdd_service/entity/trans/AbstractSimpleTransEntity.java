package uz.ciasev.ubdd_service.entity.trans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.SimpleTransEntityCreateDTOI;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractSimpleTransEntity<T extends AbstractDict> extends AbstractTransEntity {

    @Getter
    @ManyToOne
    @JoinColumn(name = "internal_id")
    private T internal;

    @Getter
    private Long externalId;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "internal_id", updatable = false, insertable = false)
    private Long internalId;

    public Long getInternalId() {
        if (internal == null) return null;
        return internal.getId();
    }

    public void construct(SimpleTransEntityCreateDTOI<T> request) {
        this.internal = request.getInternal();
        this.externalId = request.getExternalId();
    }

//    public void update(SimpleTransDictUpdateDTOI request) {
//        this.externalId = request.getExternalId();
//    }
}