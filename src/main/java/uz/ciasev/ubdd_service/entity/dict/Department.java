package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.DepartmentCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.DepartmentUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.DepartmentCacheDeserializer;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "d_department")
@NoArgsConstructor
@JsonDeserialize(using = DepartmentCacheDeserializer.class)
public class Department extends AbstractEmiDict {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id")
    private Organ organ;


    // JPA AND CRITERIA FIELDS
    @Column(name = "organ_id", insertable = false, updatable = false)
    private Long organId;

    public Long getOrganId() {
        return organ.getId();
    }

    public boolean isPartOfOrgan(Organ organ) {
        if (organ == null) return false;

        return Objects.equals(
                this.getOrganId(),
                organ.getId()
        );
    }

    public void construct(DepartmentCreateDTOI request) {
        super.construct(request);
        this.organ = request.getOrgan();
    }

    public void update(DepartmentUpdateDTOI request) {
        super.update(request);
    }
}
