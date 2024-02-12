package uz.ciasev.ubdd_service.entity.ubdd_data.insurance;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UbddInsuranceDriver implements Serializable {

     @Size(max = 255, message = ErrorCode.INSURANCE_DRIVER_PINPP_MAX_SIZE)
     private String pinpp;

     @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
     private String firstName;

     @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
     private String lastName;

     @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
     private String secondName;

     @JsonFormat(pattern = "yyyy-MM-dd")
     private LocalDate birthDate;

//     @JsonProperty(value = "relationDegreeId")
     private Long relationDegreeId;
//     private RelationDegree relationDegree;

     @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
     private String passport;

     @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
     private String drivingLicense;
}