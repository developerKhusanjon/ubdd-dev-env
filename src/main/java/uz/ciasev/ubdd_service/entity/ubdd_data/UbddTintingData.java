package uz.ciasev.ubdd_service.entity.ubdd_data;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTintingDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.TintingCategory;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ubdd_tinting_data")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UbddTintingData {

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

    private LocalDate fromDate;
    private LocalDate toDate;
    private String vehicleNumber;

    @ManyToOne
    @JoinColumn(name = "tinting_category_id")
    private TintingCategory tintingCategory;

    private Long vehicleId;

    public UbddTintingData(UbddTintingDTO dto) {

        apply(dto);
    }

    public void apply(UbddTintingDTO dto) {

        this.fromDate = dto.getFromDate();
        this.toDate = dto.getToDate();
        this.vehicleNumber = dto.getVehicleNumber();
        this.tintingCategory = dto.getTintingCategory();
        this.vehicleId = dto.getVehicleId();
    }
}
