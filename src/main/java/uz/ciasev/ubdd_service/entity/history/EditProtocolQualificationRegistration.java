package uz.ciasev.ubdd_service.entity.history;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.utils.types.ArticlePairJson;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "h_edit_protocol_qualification_registration")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class EditProtocolQualificationRegistration extends Registration {

    private String action;

    private Long protocolId;

    private Boolean fromJuridic;

    private Boolean toJuridic;

    private Long fromArticlePartId;

    private Long toArticlePartId;

    private Long fromArticleViolationTypeId;

    private Long toArticleViolationTypeId;

    private String fromInn;

    private String toInn;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Long> fromRepeatabilityProtocolsId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Long> toRepeatabilityProtocolsId;

    @Setter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<ArticlePairJson> toAdditionArticles;


}
