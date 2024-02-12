package uz.ciasev.ubdd_service.dto.internal;

import lombok.Data;

import java.util.List;

@Data
public class ProtocolRequirementDTO {
    private String number;
    private String fio;
    private String birthDate;
    private String organ;
    private String place;

    private Integer row;
    private String registrationDate;
    private List<String> articles;
    private String status;
    private String considerDate;
    private Boolean is34;
    private String decisionNumber;
    private String mainPunishment;
    private String additionalPunishment;
    private String compensation;
}
