package uz.ciasev.ubdd_service.mvd_core.expapi.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeRequest;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeResponse;

import java.util.List;

public interface UserLoginTimeService {

    void save(UserLoginTimeRequest request);

    Page<UserLoginTimeResponse> findAllByUsername(String username, Pageable pageable);

    List<UserLoginTime>getByUserId(Long userId);


}
