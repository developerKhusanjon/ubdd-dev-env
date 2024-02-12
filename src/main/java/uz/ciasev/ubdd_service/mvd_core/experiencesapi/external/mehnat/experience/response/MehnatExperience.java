package uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.experience.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MehnatExperience {

    @JsonProperty("company_inn")
    private String companyInn;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("contract_date")
    private String contractDate;

    @JsonProperty("contract_number")
    private String contractNumber;

    @JsonProperty("end_date")
    private String endDate;

    private String kodp;

    @JsonProperty("kodp_type")
    private String kodpType;

    @JsonProperty("order_date")
    private String orderDate;

    @JsonProperty("order_number")
    private String orderNumber;

    @JsonProperty("person_pin")
    private String personPin;

    @JsonProperty("position_name")
    private String positionName;

    @JsonProperty("soato_code")
    private String soatoCode;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("structure_name")
    private String structureName;

    @JsonProperty("transaction_id")
    private int transactionId;

    @JsonProperty("workplace_address")
    private String workplaceAddress;

    @JsonProperty("employment_status")
    private Integer employmentStatus;
}
