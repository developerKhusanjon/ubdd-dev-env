package uz.ciasev.ubdd_service.entity.trans.mib;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class MibTransferAbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String toString() {
        return MessageFormat.format("{0}[id={1}]", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MibTransferAbstractEntity that = (MibTransferAbstractEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}