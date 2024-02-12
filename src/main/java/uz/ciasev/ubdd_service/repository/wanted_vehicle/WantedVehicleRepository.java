package uz.ciasev.ubdd_service.repository.wanted_vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.wanted_vehicle.Vehicle;
import uz.ciasev.ubdd_service.entity.wanted_vehicle.WantedVehicleFilterProjection;

import java.util.List;

@Repository
public interface WantedVehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT wc.id, " +
            "wc.external_id as externalId, " +
            "vehicle.number as vehicleNumber, " +
            "wc.vehicle_color as vehicleColor, " +
            "wc.vehicle_brand as vehicleBrand," +
            "wc.wanted_reason_id as wantedReasonId, " +
            "organ.name ->> 'lat' as organName, " +
            "region.name ->> 'lat' as regionName, " +
            "district.name ->> 'lat' as districtName, " +
            "wc.open_inspector_info as inspectorInfo, " +
            "wc.open_inspector_phone as inspectorPhone, " +
            "wc.open_document_number as documentNumber, " +
            "wc.open_document_date as documentDate, " +
            "wc.open_document_fabula as documentFabula, " +
            "wc.is_closed as isClosed, " +
            "wc.close_inspector_info as vehicleArrestInspectorInfo," +
            "wc.close_inspector_phone as vehicleArrestInspectorPhone, " +
            "va.arrest_time as vehicleArrestDate, " +
            "va.arrest_place_id as vehicleArrestPlaceId, " +
            "dvap.name ->>'lat' as vehicleArrestPlaceName " +
            "FROM wanted_vehicle.wanted_card as wc " +
            "LEFT JOIN wanted_vehicle.wanted_card_vehicle_arrest wcva on wcva.wanted_card_id= wc.id " +
            "LEFT JOIN wanted_vehicle.vehicle_arrest va on va.id = wcva.vehicle_arrest_id " +
            "JOIN wanted_vehicle.vehicle vehicle on vehicle.id = wc.vehicle_id " +
            "LEFT JOIN wanted_vehicle.d_wanted_reason dwr on dwr.id =wc.wanted_reason_id " +
            "LEFT JOIN wanted_vehicle.d_wanted_organ organ on organ.id =wc.wanted_organ_id " +
            "LEFT JOIN wanted_vehicle.d_vehicle_arrest_place dvap on dvap.id =va.arrest_place_id " +
            "LEFT JOIN core_v0.d_region region on region.id=wc.region_id " +
            "LEFT JOIN core_v0.d_district district on district.id =wc.district_id " +
            "where wc.is_deleted = false and vehicle.number =:number order  by wc.created_time desc ",nativeQuery = true)
    List<WantedVehicleFilterProjection> findAllWantedCardByVehicle(@Param("number") String number);

}
