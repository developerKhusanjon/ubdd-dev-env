package uz.ciasev.ubdd_service.entity.ubdd_data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataBind;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindDTO;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ubdd_data_to_protocol_bind")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UbddDataToProtocolBind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    private LocalDateTime createdTime = LocalDateTime.now();

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime editedTime;

    private Long userId;

    @Setter(AccessLevel.NONE)
    @Column(name = "protocol_id")
    private Long protocolId;

    private Long ubddDrivingLicenseDataId;
    private Long ubddTexPassDataId;
    private Long ubddTintingDataId;
    private Long ubddInsuranceDataId;
    private Long vehicleArrestId;
    private Long ubddAttorneyLetterDataId;
    private Long ubddVehicleInspectionDataId;
    private boolean isOld = false;

    // JPA AND CRITERIA FIELD ONLY

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id", insertable = false, updatable = false)
    private Protocol protocol;

    public UbddDataToProtocolBind(UbddDataToProtocolBindDTO dto) {
        this.protocolId = dto.getProtocolId();
        apply(dto);
    }

    public UbddDataToProtocolBind(Long protocolId, UbddDataBind dto) {
        this.protocolId = protocolId;
        apply(dto);
    }

    public UbddDataToProtocolBind(Long protocolId) {
        this.protocolId = protocolId;
    }

    public void apply(UbddDataBind dto) {
        this.ubddDrivingLicenseDataId = dto.getUbddDrivingLicenseDataId();
        this.ubddTexPassDataId = dto.getUbddTexPassDataId();
        this.ubddTintingDataId = dto.getUbddTintingDataId();
        this.ubddInsuranceDataId = dto.getUbddInsuranceDataId();
        this.vehicleArrestId = dto.getVehicleArrestId();
        this.ubddAttorneyLetterDataId = dto.getUbddAttorneyLetterDataId();
        this.ubddVehicleInspectionDataId = dto.getUbddVehicleInspectionDataId();
        this.isOld = dto.isOld();
    }
}
