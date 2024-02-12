package uz.ciasev.ubdd_service.entity.dict.article;

import lombok.*;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class PenaltyRangeForDateDTO {

    private Optional<Long> personMin;

    private Optional<Long> personMax;

    private Optional<Long> juridicMin;

    private Optional<Long> juridicMax;
}
