package uz.ciasev.ubdd_service.entity.protocol;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.*;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDGroup;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.ubdd_data.ProtocolUbddTexPassData;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.service.protocol.ProtocolCreateRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "protocol")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Protocol implements Serializable, AdmEntity, AdmProtocol {

    @Getter
    @Transient
    private final EntityNameAlias entityNameAlias = EntityNameAlias.PROTOCOL;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "protocol_id_seq")
    @SequenceGenerator(name = "protocol_id_seq", sequenceName = "protocol_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    // кто ввел протакол в систему
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id", updatable = false)
    private User createdUser;

    // кто зарегестрирвал протакол
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Getter
    @Column(updatable = false)
    private String series;

    @Getter
    @Column(updatable = false)
    private String number;

    @Getter
    @Setter
    private String oldSeries;

    @Getter
    @Setter
    private String oldNumber;

    @Getter
    @Setter
    private LocalDateTime violationTime;

    @Getter
    @Setter
    private LocalDateTime registrationTime;

    @Getter
    @ManyToOne
    @JoinColumn(name = "inspector_region_id", updatable = false)
    private Region inspectorRegion;

    @Getter
    @ManyToOne
    @JoinColumn(name = "inspector_district_id", updatable = false)
    private District inspectorDistrict;

    @Getter
    @ManyToOne
    @JoinColumn(name = "inspector_position_id", updatable = false)
    private Position inspectorPosition;

    @Getter
    @ManyToOne
    @JoinColumn(name = "inspector_rank_id", updatable = false)
    private Rank inspectorRank;

    @Getter
    @Column(updatable = false)
    private String inspectorFio;

    @Getter
    @Column(updatable = false)
    private String inspectorWorkCertificate;

    @Getter
    @Column(updatable = false)
    private String inspectorInfo;

    @Getter
    @Column(updatable = false)
    private String inspectorSignature;

    @Getter
    @ManyToOne
    @JoinColumn(name = "organ_id", updatable = false)
    private Organ organ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", updatable = false)
    private Department department;

    @Getter
    @ManyToOne
    @JoinColumn(name = "region_id", updatable = false)
    private Region region;

    @Getter
    @ManyToOne
    @JoinColumn(name = "district_id", updatable = false)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mtp_id", updatable = false)
    private Mtp mtp;

    @Getter
    @Column(updatable = false)
    private String address;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "violator_detail_id")
    private ViolatorDetail violatorDetail;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "juridic_id")
    private Juridic juridic;

    @Getter
    @Setter
    private Boolean isJuridic;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "article_part_id")
    private ArticlePart articlePart;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "article_violation_type_id")
    private ArticleViolationType articleViolationType;

    @Getter
    @Column(updatable = false)
    private boolean isAgree;

    @Getter
    @Column(updatable = false)
    private boolean isFamiliarize;

    @Getter
    @Column(updatable = false)
    private String explanatory;

    @Getter
    @Setter
    private String fabula;

    @Getter
    @Setter
    private String fabulaAdditional;


    @Getter
    @Column(updatable = false)
    private Double latitude;

    @Getter
    @Column(updatable = false)
    private Double longitude;

    @Getter
    @Column(updatable = false)
    private String audioUri;

    @Getter
    @Column(updatable = false)
    private String videoUri;


    @Getter
    @Column(updatable = false)
    private boolean isMain;

    @Getter
    @Column(updatable = false)
    private boolean isDeleted;


    @Getter
    @Column(updatable = false)
    private Boolean isTablet;

    @Getter
    @Column(updatable = false)
    private Boolean isRaid;

    @Getter
    @Column(updatable = false)
    private Boolean isPaper;


    @Getter
    @Column(updatable = false)
    private String externalId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubdd_group_id")
    private UBDDGroup ubddGroup;

    @Getter
    @Setter
    private String trackNumber;

    @Getter
    @Setter
    private String vehicleNumber;


    // JPA AND CRITERIA FIELD ONLY

    @Column(name = "created_user_id", insertable = false, updatable = false)
    private Long createdUserId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "article_id", insertable = false, updatable = false)
    private Long articleId;

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

    @Column(name = "article_violation_type_id", insertable = false, updatable = false)
    private Long articleViolationTypeId;

    @Column(name = "violator_detail_id", insertable = false, updatable = false)
    private Long violatorDetailId;

    @Column(name = "organ_id", insertable = false, updatable = false)
    private Long organId;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    @Column(name = "mtp_id", insertable = false, updatable = false)
    private Long mtpId;

    @Column(name = "juridic_id", insertable = false, updatable = false)
    private Long juridicId;

    @Column(name = "ubdd_group_id", insertable = false, updatable = false)
    private Long ubddGroupId;

    @Column(name = "inspector_region_id", insertable = false, updatable = false)
    private Long inspectorRegionId;

    @Column(name = "inspector_district_id", insertable = false, updatable = false)
    private Long inspectorDistrictId;

    @Column(name = "inspector_position_id", insertable = false, updatable = false)
    private Long inspectorPositionId;

    @Column(name = "inspector_rank_id", insertable = false, updatable = false)
    private Long inspectorRankId;

    @OneToOne(mappedBy = "protocol", fetch = FetchType.LAZY)
    private ProtocolUbddData ubddData;

    @OneToOne(mappedBy = "protocol", fetch = FetchType.LAZY)
    private UbddDataToProtocolBind ubddDataBind;

    @OneToOne(mappedBy = "protocol", fetch = FetchType.LAZY)
    private ProtocolUbddTexPassData ubddTexPassData;


    @Builder
    public Protocol(ProtocolCreateRequest request) {
        this.createdUser = request.getCreatedUser();

        this.user = request.getUser();
        this.inspectorInfo = request.getInspectorInfo();
        this.inspectorFio = request.getInspectorFio();
        this.inspectorRegion = request.getInspectorRegion();
        this.inspectorDistrict = request.getInspectorDistrict();
        this.inspectorRank = request.getInspectorRank();
        this.inspectorPosition = request.getInspectorPosition();
        this.inspectorWorkCertificate = request.getInspectorWorkCertificate();
        this.organ = request.getOrgan();
        this.department = request.getDepartment();
        this.inspectorSignature = request.getInspectorSignature();

        this.series = request.getSeries();
        this.number = request.getNumber();
        this.oldSeries = request.getOldSeries();
        this.oldNumber = request.getOldNumber();

        this.violationTime = request.getViolationTime();
        this.registrationTime = request.getRegistrationTime();

        this.region = request.getRegion();
        this.district = request.getDistrict();
        this.mtp = request.getMtp();
        this.address = request.getAddress();

        this.violatorDetail = request.getViolatorDetail();
        this.juridic = request.getJuridic();
        this.isJuridic = request.getIsJuridic();

        this.article = request.getArticle();
        this.articlePart = request.getArticlePart();
        this.articleViolationType = request.getArticleViolationType();

        this.isAgree = request.isAgree();
        this.isFamiliarize = request.isFamiliarize();
        this.explanatory = request.getExplanatory();
        this.fabula = request.getFabula();
        this.fabulaAdditional = request.getFabulaAdditional();

        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
        this.audioUri = request.getAudioUri();
        this.videoUri = request.getVideoUri();

        this.isTablet = request.getIsTablet();
        this.isRaid = request.getIsRaid();
        this.isPaper = request.getIsPaper();

        this.externalId = request.getExternalId();

        this.ubddGroup = request.getUbddGroup();
        this.trackNumber = request.getTrackNumber();
        this.vehicleNumber = request.getVehicleNumber();


        this.isMain = true;
        this.isDeleted = false;
    }

    public Long getCreatedUserId() {
        if (this.createdUser == null) return null;
        return createdUser.getId();
    }

    public Long getUserId() {
        if (this.user == null) return null;
        return user.getId();
    }

    public Long getArticleId() {
        if (this.article == null) return null;
        return article.getId();
    }

    public Long getArticlePartId() {
        if (this.articlePart == null) return null;
        return articlePart.getId();
    }

    public Long getArticleViolationTypeId() {
        if (this.articleViolationType == null) return null;
        return articleViolationType.getId();
    }

    public Long getViolatorDetailId() {
        if (this.violatorDetail == null) return null;
        return violatorDetail.getId();
    }

    public Long getOrganId() {
        if (this.organ == null) return null;
        return organ.getId();
    }

    public Long getDepartmentId() {
        if (this.department == null) return null;
        return department.getId();
    }

    public Long getRegionId() {
        if (this.region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (this.district == null) return null;
        return district.getId();
    }

    public Long getMtpId() {
        if (this.mtp == null) return null;
        return mtp.getId();
    }

    public Long getJuridicId() {
        if (this.juridic == null) return null;
        return juridic.getId();
    }

    public Long getUbddGroupId() {
        if (this.ubddGroup == null) return null;
        return ubddGroup.getId();
    }

    public Long getInspectorRegionId() {
        if (this.inspectorRegion == null) return null;
        return inspectorRegion.getId();
    }

    public Long getInspectorDistrictId() {
        if (this.inspectorDistrict == null) return null;
        return inspectorDistrict.getId();
    }

    public Long getInspectorPositionId() {
        if (this.inspectorPosition == null) return null;
        return inspectorPosition.getId();
    }

    public Long getInspectorRankId() {
        if (this.inspectorRank == null) return null;
        return inspectorRank.getId();
    }

    public boolean isArchived() {
        return Optional.ofNullable(violatorDetail)
                .map(ViolatorDetail::getViolator)
                .map(Violator::getIsArchived)
                .orElse(false);
    }

    public boolean isActive() {
        return !isDeleted && !isArchived();
    }

    public void setMainArticle(ProtocolArticle mainArticle) {
        this.article = mainArticle.article;
        this.articlePart = mainArticle.articlePart;
        this.articleViolationType = mainArticle.articleViolationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Protocol protocol = (Protocol) o;
        return id != null && Objects.equals(id, protocol.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
