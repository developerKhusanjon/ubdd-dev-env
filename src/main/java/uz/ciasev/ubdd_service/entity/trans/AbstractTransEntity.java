package uz.ciasev.ubdd_service.entity.trans;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractTransEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Override
    public String toString() {
        return MessageFormat.format("{0}[id={1}]", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTransEntity that = (AbstractTransEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}