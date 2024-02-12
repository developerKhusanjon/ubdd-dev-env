package uz.ciasev.ubdd_service.dto.internal.response.adm;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DamageDetailResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime editedTime;
    private Long userId;
    private Long protocolId;
    private String protocolSeries;
    private String protocolNumber;
    private Long victimId;
    private Long victimTypeId;
    private Long damageId;
    private Long damageTypeId;
    private Long amount;
}