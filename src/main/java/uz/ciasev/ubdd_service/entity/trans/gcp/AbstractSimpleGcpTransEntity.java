package uz.ciasev.ubdd_service.entity.trans.gcp;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.SimpleGcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public abstract class AbstractSimpleGcpTransEntity<T extends AbstractDict> extends AbstractGcpTransEntity {

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internal_id")
    private T internal;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "internal_id", updatable = false, insertable = false)
    private Long internalId;

    public Long getInternalId() {
        if (internal == null) return null;
        return internal.getId();
    }

    public void construct(SimpleGcpTransEntityCreateDTOI<T> request, MultiLanguage name) {
        super.construct(request);
        this.internal = request.getInternal();
        this.name = name;
    }

//    public void update(SimpleTransDictUpdateDTOI request) {
//        this.externalId = request.getExternalId();
//    }
}