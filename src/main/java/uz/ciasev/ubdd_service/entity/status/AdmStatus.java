package uz.ciasev.ubdd_service.entity.status;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.utils.deserializer.dict.AdmStatusCacheDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@Entity
@Table(name = "d_adm_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@JsonDeserialize(using = AdmStatusCacheDeserializer.class)
public class AdmStatus {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    protected AdmStatusAlias alias;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage caseName;

    @Getter
    private Integer rank;

    @Getter
    private String color;

    public boolean is(AdmStatusAlias o) {
        return o.equals(getAlias());
    }

    public boolean not(AdmStatusAlias o) {
        return !is(o);
    }

    public boolean oneOf(AdmStatusAlias o1, AdmStatusAlias o2) {
        return is(o1) || is(o2);
    }

    public boolean notOneOf(AdmStatusAlias o1, AdmStatusAlias o2) {
        return not(o1) && not(o2);
    }

    public boolean oneOf(AdmStatusAlias o1, AdmStatusAlias o2, AdmStatusAlias o3) {
        return oneOf(o1, o2) || is (o3);
    }
}
