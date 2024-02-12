package uz.ciasev.ubdd_service.service;

import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.entity.LastEmploymentInfoOwner;
import uz.ciasev.ubdd_service.entity.user.User;

public interface LastEmploymentInfoService {

    void addLastEmploymentInfoToOwner(User user, LastEmploymentInfoOwner owner, LastEmploymentInfoDTO lastEmploymentInfoDTO);

    LastEmploymentInfoDTO getDTOByOwner(LastEmploymentInfoOwner owner);
}