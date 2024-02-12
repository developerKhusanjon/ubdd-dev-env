package uz.ciasev.ubdd_service.dto.internal;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.wanted_vehicle.WantedVehicleFilterProjection;

import java.time.LocalDateTime;

@Data
public class WantedVehicleDTO {
    private Long id;
    private String externalId;
    private String vehicleNumber;
    private String vehicleBrand;
    private String vehicleColor;
    private Long wantedReasonId;
    private String devisionName;
    private String inspectorInfo;
    private String inspectorPhone;
    private String documentNumber;
    private String documentDate;
    private String documentFabula;
    private Boolean isClosed;
    private String vehicleArrestInspectorInfo;
    private String vehicleArrestInspectorPhone;
    private LocalDateTime vehicleArrestDate;
    private Long vehicleArrestPlaceId;
    private String vehicleArrestPlaceName;

    public WantedVehicleDTO(WantedVehicleFilterProjection projection) {
        this.id = projection.getId();
        this.externalId = projection.getExternalId();
        this.vehicleNumber = projection.getVehicleNumber();
        this.vehicleBrand = projection.getVehicleBrand();
        this.vehicleColor = projection.getVehicleColor();
        this.wantedReasonId = projection.getWantedReasonId();
        this.devisionName = String.join("|", projection.getOrganName(), projection.getRegionName(), projection.getDistrictName());
        this.inspectorInfo = projection.getInspectorInfo();
        this.inspectorPhone = projection.getInspectorPhone();
        this.isClosed = projection.getIsClosed();
        this.vehicleArrestInspectorInfo = projection.getVehicleArrestInspectorInfo();
        this.vehicleArrestInspectorPhone = projection.getVehicleArrestInspectorPhone();
        this.vehicleArrestDate = projection.getVehicleArrestDate();
        this.vehicleArrestPlaceId = projection.getVehicleArrestPlaceId();
        this.vehicleArrestPlaceName = projection.getVehicleArrestPlaceName();
        this.documentDate = projection.getDocumentDate();
        this.documentNumber = projection.getDocumentNumber();
        this.documentFabula = projection.getDocumentFabula();
    }
}
