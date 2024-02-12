package uz.ciasev.ubdd_service.dto.internal.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProtocolSearchFilterRequestDTO {

    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private String protocolNumber;
    private LocalDateTime registrationTimeFrom;
    private LocalDateTime registrationTimeTo;
    private Long articleId;
    private Long articlePartId;
    private Long organId;
    private Long regionId;
    private Long districtId;
    private Long personDocumentTypeId;
    private String documentNumber;
}
