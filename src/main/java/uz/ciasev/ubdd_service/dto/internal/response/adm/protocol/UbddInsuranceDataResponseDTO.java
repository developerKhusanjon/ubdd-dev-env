package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceAccident;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceData;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceDriver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UbddInsuranceDataResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final String applicantName;
    private final String applicantType;
    private final String vehicleNumber;
    private final String vehicleBrand;
    private final List<UbddInsuranceDriver> drivers;
    private final String insuranceOrganization;
    private final String policySeries;
    private final String policyNumber;
    private final String policyType;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final Long insurancePremium;
    private final Long insuranceSum;
    private final List<UbddInsuranceAccident> accidents;

    public UbddInsuranceDataResponseDTO(UbddInsuranceData insurance) {
        this.id = insurance.getId();
        this.createdTime = insurance.getCreatedTime();
        this.editedTime = insurance.getEditedTime();
        this.userId = insurance.getUserId();
        this.applicantName = insurance.getApplicantName();
        this.applicantType = insurance.getApplicantType();
        this.vehicleNumber = insurance.getVehicleNumber();
        this.vehicleBrand = insurance.getVehicleBrand();
        this.drivers = insurance.getDrivers();
        this.insuranceOrganization = insurance.getInsuranceOrganization();
        this.policySeries = insurance.getPolicySeries();
        this.policyNumber = insurance.getPolicyNumber();
        this.policyType = insurance.getPolicyType();
        this.fromDate = insurance.getFromDate();
        this.toDate = insurance.getToDate();
        this.insurancePremium = insurance.getInsurancePremium();
        this.insuranceSum = insurance.getInsuranceSum();
        this.accidents = insurance.getAccidents();
    }
}
