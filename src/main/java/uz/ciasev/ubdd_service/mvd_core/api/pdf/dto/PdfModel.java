package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PdfModel {

    private final String modelName;
    private final Long modelId;
    private final LocalDateTime generateTime;
    private final EntityNameAlias entityNameAlias;

    public PdfModel(AdmEntity entity) {
        this.modelId = entity.getId();
        this.entityNameAlias = entity.getEntityNameAlias();
        this.modelName = entity.getEntityNameAlias().name();
        this.generateTime = LocalDateTime.now();
    }

}
