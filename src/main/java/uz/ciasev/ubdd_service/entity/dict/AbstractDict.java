package uz.ciasev.ubdd_service.entity.dict;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.DictUpdateDTOI;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public abstract class AbstractDict implements Serializable, DictEntity {

    private static final String EMPTY_VALUE = "~~~~~~~~~~~~~";

    @Transient
    protected static MultiLanguage defaultName = new MultiLanguage(EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE);

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    protected MultiLanguage name;

    @Getter
    protected String code;

    @Getter
    protected LocalDate openedDate;

    @Getter
    protected LocalDate closedDate;

    @Getter
    protected Boolean isActive = true;

    protected void constructBase(DictCreateDTOI request) {
        name = request.getName();
        code = request.getCode();
        isActive = true;
        openedDate = LocalDate.now();
        closedDate = null;
    }

    public void updateBase(DictUpdateDTOI request) {
        name = request.getName();
        code = request.getCode();
    }

    public void open() {
        isActive = true;
        openedDate = LocalDate.now();
        closedDate = null;
    }

    public void close() {
        isActive = false;
        closedDate = LocalDate.now();
    }

    public MultiLanguage getName() {
        return Objects.nonNull(name) ? name : defaultName;
    }

    public String getDefaultName() {
        return Optional.ofNullable(name).map(MultiLanguage::getLat).orElse("Value not present");
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[id={1}]", this.getClass().getName(), getId());
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AbstractDict that = (AbstractDict) o;
//        return Objects.equals(getId(), that.getId());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbstractDict that = (AbstractDict) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public boolean isDeactivated() {
        return !isActive;
    }
}