package uz.ciasev.ubdd_service.entity.wanted_vehicle;

import java.time.LocalDateTime;

public interface WantedVehicleFilterProjection {

    Long getId();

    String getExternalId();

    String getVehicleNumber();
    String getVehicleBrand();
    String getVehicleColor();

    Long getWantedReasonId();

    String getOrganName();

    String getRegionName();

    String getDistrictName();

    String getInspectorInfo();
    String getDocumentNumber();
    String getDocumentDate();
    String getDocumentFabula();

    LocalDateTime getRegistrationTime();

    String getInspectorFio();

    String getInspectorPhone();

    Boolean getIsClosed();

    String getVehicleArrestInspectorInfo();

    String getVehicleArrestInspectorPhone();

    LocalDateTime getVehicleArrestDate();

    Long getVehicleArrestPlaceId();

    String getVehicleArrestPlaceName();

}
