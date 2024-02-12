package uz.ciasev.ubdd_service.service.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDGroup;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;

import java.time.LocalDateTime;

@Data
public class ProtocolCreateRequest {

    private User createdUser;

    private User user;

    private String series;

    private String number;

    private String oldSeries;

    private String oldNumber;

    private LocalDateTime violationTime;

    private LocalDateTime registrationTime;

    private Region inspectorRegion;

    private District inspectorDistrict;

    private Position inspectorPosition;

    private Rank inspectorRank;

    private String inspectorFio;

    private String inspectorWorkCertificate;

    private String inspectorInfo;

    private String inspectorSignature;

    private Organ organ;

    private Department department;

    private Region region;

    private District district;

    private Mtp mtp;

    private String address;

    private ViolatorDetail violatorDetail;

    private Juridic juridic;

    private Boolean isJuridic;

    private Article article;

    private ArticlePart articlePart;

    private ArticleViolationType articleViolationType;

    private boolean isAgree;

    private boolean isFamiliarize;

    private String explanatory;

    private String fabula;

    private String fabulaAdditional;

    private Double latitude;

    private Double longitude;

    private String audioUri;

    private String videoUri;

    private Boolean isTablet;

    private Boolean isRaid;

    private Boolean isPaper;

    private String externalId;

    private UBDDGroup ubddGroup;

    private String trackNumber;

    private String vehicleNumber;

    public void setFamiliarize(Boolean familiarize) {
        if (familiarize == null) {
            familiarize = false;
        }
        isFamiliarize = familiarize;
    }
}
