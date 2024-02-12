package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDConfiscatedCategoryCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDConfiscatedCategoryUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.UBDDConfiscatedCategoryDeserializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "d_ubdd_confiscated_category")
@NoArgsConstructor
@JsonDeserialize(using = UBDDConfiscatedCategoryDeserializer.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class UBDDConfiscatedCategory extends UbddAbstractDictEntity {

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "ids")
    private List<Long> ids;

    public void construct(UBDDConfiscatedCategoryCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(UBDDConfiscatedCategoryUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(UBDDConfiscatedCategoryUpdateDTOI request) {
        this.ids = Optional.ofNullable(request.getIds()).orElse(List.of(this.getId()));
    }
}
