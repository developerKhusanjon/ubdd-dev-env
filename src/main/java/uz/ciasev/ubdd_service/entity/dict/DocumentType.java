package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.DocumentTypeCacheDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@Entity
@Table(name = "d_document_type")
@NoArgsConstructor
@JsonDeserialize(using = DocumentTypeCacheDeserializer.class)
public class DocumentType extends AbstractAliasedDict<DocumentTypeAlias> {

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage description;

    @Getter
    private Boolean preventAttach;

    @Override
    public void construct(DictCreateDTOI request) {
        super.construct(request);
        this.description = request.getName();
        this.preventAttach = false;
    }

    @Override
    protected DocumentTypeAlias getDefaultAliasValue() {
        return DocumentTypeAlias.OTHER;
    }
}