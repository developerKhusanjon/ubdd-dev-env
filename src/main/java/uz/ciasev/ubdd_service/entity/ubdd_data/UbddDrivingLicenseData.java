package uz.ciasev.ubdd_service.entity.ubdd_data;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDriverLicenseDocumentCategoryDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDriverLicenseDTO;
import uz.ciasev.ubdd_service.entity.Address;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ubdd_driving_license_data")
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@EntityListeners(AuditingEntityListener.class)
public class UbddDrivingLicenseData {

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

    // OWNER
    private String vehicleOwnerPinpp;
    private LocalDate vehicleOwnerBirthDate;
    private String vehicleOwnerLastName;
    private String vehicleOwnerFirstName;
    private String vehicleOwnerSecondName;

    // DOCUMENT
    private LocalDate drivingLicenseFromDate;
    private LocalDate drivingLicenseToDate;
    private String drivingLicenseIssuedBy;
    private String drivingLicenseSerial;
    private String drivingLicenseNumber;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<UbddDriverLicenseDocumentCategoryDTO> categories;

    @ManyToOne
    @JoinColumn(name = "driving_license_given_address_id")
    private Address drivingLicenseGivenAddress;

    public UbddDrivingLicenseData(UbddDriverLicenseDTO dto) {

        apply(dto);
    }

    public void apply(UbddDriverLicenseDTO dto) {

        // OWNER
        this.vehicleOwnerPinpp = dto.getVehicleOwnerPinpp();
        this.vehicleOwnerBirthDate = dto.getVehicleOwnerBirthDate();
        this.vehicleOwnerLastName = dto.getVehicleOwnerLastName();
        this.vehicleOwnerFirstName = dto.getVehicleOwnerFirstName();
        this.vehicleOwnerSecondName = dto.getVehicleOwnerSecondName();

        // DOCUMENT
        this.drivingLicenseFromDate = dto.getDrivingLicenseFromDate();
        this.drivingLicenseToDate = dto.getDrivingLicenseToDate();
        this.drivingLicenseIssuedBy = dto.getDrivingLicenseIssuedBy();
        this.drivingLicenseSerial = dto.getDrivingLicenseSerial();
        this.drivingLicenseNumber = dto.getDrivingLicenseNumber();

        this.categories = dto.getCategories();
    }
}
