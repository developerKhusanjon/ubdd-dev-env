package uz.ciasev.ubdd_service.entity.ubdd_data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddVehicleInspectionDTO;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "ubdd_vehicle_inspection_data")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UbddVehicleInspectionData {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime editedTime;

    @Getter
    @Setter
    private Long userId;

    @Getter
    private String vehicleNumber;

    @Getter
    private Boolean isInspectionPass;

    @Getter
    private LocalDateTime inspectionTime;

    @Getter
    private LocalDateTime nextInspectionTime;

    @Getter
    private String inspectorInfo;

    @Getter
    private String additionally;


    public UbddVehicleInspectionData(UbddVehicleInspectionDTO dto) {
        apply(dto);
    }

    public void apply(UbddVehicleInspectionDTO dto) {
        this.vehicleNumber = dto.getVehicleNumber();
        this.isInspectionPass = dto.getIsInspectionPass();
        this.inspectionTime = dto.getInspectionTime();
        this.nextInspectionTime = dto.getNextInspectionTime();
        this.inspectorInfo = dto.getInspectorInfo();
        this.additionally = dto.getAdditionally();
    }
}
