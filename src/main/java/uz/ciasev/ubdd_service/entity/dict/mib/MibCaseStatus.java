package uz.ciasev.ubdd_service.entity.dict.mib;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalDictEntity;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.MibCaseStatusCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.MibCaseStatusUpdateDTOI;
import uz.ciasev.ubdd_service.utils.converter.MibCaseStatusTypeIdToAliasConverter;

import javax.persistence.*;


@Entity
@Table(name = "d_mib_case_status")
@NoArgsConstructor
public class MibCaseStatus extends AbstractExternalDictEntity implements AliasedDictEntity<MibCaseStatusAlias> {

    @Getter
    @Convert(converter = MibCaseStatusTypeIdToAliasConverter.class)
    @Column(name = "type_id")
    protected MibCaseStatusAlias alias;

    @Getter
    private Boolean isSuspensionArticle;


    @Column(name = "type_id", insertable = false, updatable = false)
    private Long typeId;

    public void construct(MibCaseStatusCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(MibCaseStatusUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(MibCaseStatusUpdateDTOI request) {
        this.alias = request.getAlias();
        this.isSuspensionArticle = request.getIsSuspensionArticle();
    }
}
