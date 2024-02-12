package uz.ciasev.ubdd_service.service.user;

import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.user.User;

public interface UserService {

    User findById(Long id);

    void checkIfUserActive(User user);

    boolean isUsersExistIn(Organ organ, Department department, Region region, District district);

    Organ getUserRelateOrgan(User user);
}
