package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AdmCaseSendingResponseDTO {

    private Set<Long> organIds;
    private Set<Long> departmentIds;
}
