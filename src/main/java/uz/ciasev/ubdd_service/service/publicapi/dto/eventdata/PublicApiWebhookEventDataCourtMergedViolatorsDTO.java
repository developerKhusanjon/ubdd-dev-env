package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PublicApiWebhookEventDataCourtMergedViolatorsDTO {

    private Long violatorId;

    private String firstNameLat;

    private String secondNameLat;

    private String lastNameLat;

    private LocalDate birthDate;

}
