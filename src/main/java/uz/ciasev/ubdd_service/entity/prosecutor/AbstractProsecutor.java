package uz.ciasev.ubdd_service.entity.prosecutor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractProsecutor implements AdmEntity, Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Setter
    @Getter
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    @Setter
    @Getter
    private LocalDateTime editedTime;

    @Setter
    @Getter
    private String prosecutorInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @Setter
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @Setter
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id")
    @Setter
    private Rank rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    @Setter
    private Position position;

    @Column(name = "description")
    @Setter
    @Getter
    private String description;

    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    @Column(name = "rank_id", insertable = false, updatable = false)
    private Long rankId;

    @Column(name = "position_id", insertable = false, updatable = false)
    private Long positionId;

    public Long getUserId() {
        if (this.user == null) {
            return null;
        }
        return this.user.getId();
    }

    public Long getRegionId() {
        if (this.region == null) {
            return null;
        }
        return this.region.getId();
    }

    public Long getDistrictId() {
        if (this.district == null) {
            return null;
        }
        return this.district.getId();
    }

    public Long getRankId() {
        if (this.rank == null) {
            return null;
        }
        return this.rank.getId();
    }

    public Long getPositionId() {
        if (this.position == null) {
            return null;
        }
        return this.position.getId();
    }
}
