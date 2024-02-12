package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.ubdd.TintingCategory;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTintingData;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class UbddTintingResponseDTO {

    private Long id;
    private String vehicleNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long tintingCategoryId;
    private Long vehicleId;

    public UbddTintingResponseDTO(UbddTintingData entity) {

        this.id = entity.getId();
        this.vehicleNumber = entity.getVehicleNumber();
        this.fromDate = entity.getFromDate();
        this.toDate = entity.getToDate();
        this.tintingCategoryId = Optional.ofNullable(entity.getTintingCategory()).map(TintingCategory::getId).orElse(null);
        this.vehicleId = entity.getVehicleId();
    }
}
