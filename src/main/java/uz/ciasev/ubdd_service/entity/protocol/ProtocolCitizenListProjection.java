package uz.ciasev.ubdd_service.entity.protocol;

import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;

import java.time.LocalDateTime;

public interface ProtocolCitizenListProjection {

    Long getId();

    String getSeries();

    String getNumber();

    LocalDateTime getRegistrationTime();

    LocalDateTime getViolationTime();

    Boolean getIsMain();

    String getInspectorInfo();

    Long getViolatorId();

    String getViolatorPinpp();

    String getViolatorDocumentSeries();

    String getViolatorDocumentNumber();

    Organ getOrgan();

    Department getDepartment();

    District getDistrict();

    Region getRegion();

    Boolean getIsJuridic();

    ArticlePart getArticlePart();

    ArticleViolationType getArticleViolationType();

    String getUbddVehicleColor();

    String getUbddVehicleBrand();

    String getUbddVehicleNumber();

    String getUbddVehicleAdditional();

    Boolean getHasVictims();

    Boolean getHasParticipants();

}
