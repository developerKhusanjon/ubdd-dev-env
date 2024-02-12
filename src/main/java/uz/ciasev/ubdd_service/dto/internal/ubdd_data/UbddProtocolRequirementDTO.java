package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticlesProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolRequirementProjection;
import uz.ciasev.ubdd_service.utils.FioUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UbddProtocolRequirementDTO {

    private String fio;
    private String pinpp;
    @Setter
    private String birthDate;
    private String series;
    private String number;
    @Setter
    private String registrationDate;
    private String status;
    private Long statusId;
    private String organName;
    private Long organId;
    private String regionName;
    private Long regionId;
    private String districtName;
    private Long districtId;
    private List<ArticleBody> articles;

    public UbddProtocolRequirementDTO(ProtocolRequirementProjection projection) {
        this.fio = FioUtils.buildFullFio(
                projection.getViolatorFirstNameLat(),
                projection.getViolatorSecondNameLat(),
                projection.getViolatorSecondNameLat());
        this.pinpp = projection.getViolatorPinpp();
        this.series = projection.getProtocolSeries();
        this.number = projection.getProtocolNumber();
        this.status = projection.getAdmCaseStatus();
        this.statusId = projection.getAdmCaseStatusId();
        this.organName = projection.getOrganName();
        this.organId = projection.getOrganId();
        this.regionName = projection.getRegionName();
        this.regionId = projection.getProtocolRegionId();
        this.districtName = projection.getDistrictName();
        this.districtId = projection.getProtocolDistrictId();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class ArticleBody {
        private Long articleId;
        private Long articlePartId;
        private Long articleViolationTypeId;
        private Boolean isMain;
    }

    public void addArticle(ProtocolArticlesProjection projection) {
        if (articles == null) {
            articles = new ArrayList<>();
        }
        articles.add(new ArticleBody(projection.getArticleId(),
                projection.getArticlePartId(),
                projection.getArticleViolationTypeId(),
                projection.getIsMain()));
    }
}
