
package uz.ciasev.ubdd_service.repository.expapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.UserLoginTime;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeResponse;

import java.util.List;

public interface UserLoginTimeRepository extends JpaRepository<UserLoginTime, Long> {

    List<UserLoginTime> findAllByUserId(Long userId);

    @Query("select new uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeResponse(" +
            "userLoginTime.id, " +
            "user.username, " +
            "birthAddress.fullAddressText, " +
            "userLoginTime.createAt) " +
            "from UserLoginTime userLoginTime " +
            "join userLoginTime.user user " +
            "join user.person.birthAddress birthAddress " +
            "where :username is null or user.username = :username")
    Page<UserLoginTimeResponse> findAllByUserUsername(String username, Pageable pageable);


}
