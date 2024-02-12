package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import lombok.Data;

@Data
public class ThirdExactedDamageRequestDTO {

    private Long exactedDamageTotal;
    private Long exactedDamageCurrency;
    private Long fromWhom;
    private Long fromWhomInn;
    private Long inFavorType;
    private Long victimId;
    private Long inn;

}
