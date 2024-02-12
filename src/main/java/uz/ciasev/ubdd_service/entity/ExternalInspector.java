package uz.ciasev.ubdd_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.user.User;

@AllArgsConstructor
@Builder
@Getter
public class ExternalInspector implements Inspector {

    private final String fio;
    private final String info;
    private final Region region;
    private final District district;
    private final Organ organ;
    private final Department department;
    private final Position position;
    private final Rank rank;
    private final String workCertificate;
    private final User user;
}
