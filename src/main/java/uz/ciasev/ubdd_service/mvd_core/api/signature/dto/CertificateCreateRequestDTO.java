package uz.ciasev.ubdd_service.mvd_core.api.signature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CertificateCreateRequestDTO {

    private final String firstName;

    private final String secondName;

    private final String lastName;

    private final String organ;

    private final String department;

    private final String region;

    private final String district;

    private final String position;

    private final String rank;

}
