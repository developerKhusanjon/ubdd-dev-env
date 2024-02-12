package uz.ciasev.ubdd_service.dto.internal;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ViolatorRequirementDTO {
       private LocalDateTime createdDate;
       private String nameLat;
       private String lastName;
       private String firstname;
       private String secondName;
       private LocalDate birhtDate;
       private String residentAddress;
       private List<ProtocolRequirementDTO> protocols;
}
