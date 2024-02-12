package uz.ciasev.ubdd_service.service.generator;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AdmDocumentNumber {
    private final String series;
    private final String number;
    private final boolean isUniqueNumber;
}
