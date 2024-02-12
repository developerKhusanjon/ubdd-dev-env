package uz.ciasev.ubdd_service.entity.ubdd_data.insurance;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.UbddInsuranceDataRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "ubdd_insurance_data")
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class UbddInsuranceData extends UbddInsuranceDataAbstract {

    public UbddInsuranceData(User user) {
        this.user = user;
    }

    public void apply(UbddInsuranceDataRequestDTO request) {

        this.applicantName = request.getApplicantName();
        this.applicantType = request.getApplicantType();
        this.vehicleNumber = request.getVehicleNumber();
        this.vehicleBrand = request.getVehicleBrand();
        this.drivers = request.getDrivers();
        this.insuranceOrganization = request.getInsuranceOrganization();
        this.policySeries = request.getPolicySeries();
        this.policyNumber = request.getPolicyNumber();
        this.policyType = request.getPolicyType();
        this.fromDate = request.getFromDate();
        this.toDate = request.getToDate();
        this.insurancePremium = request.getInsurancePremium();
        this.insuranceSum = request.getInsuranceSum();
        this.accidents = request.getAccidents();
    }
}
