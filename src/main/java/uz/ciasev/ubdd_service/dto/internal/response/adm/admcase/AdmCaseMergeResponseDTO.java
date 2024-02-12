package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AdmCaseMergeResponseDTO {

    private Long violatorId;
}
