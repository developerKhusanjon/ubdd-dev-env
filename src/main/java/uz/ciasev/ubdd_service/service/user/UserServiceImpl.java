package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.security.UserDeactivatedException;
import uz.ciasev.ubdd_service.repository.user.UserRepository;
import uz.ciasev.ubdd_service.service.dict.OrganDictionaryService;
import uz.ciasev.ubdd_service.specifications.UserSpecifications;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSpecifications userSpecifications;
    private final OrganDictionaryService organService;

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(User.class, id));
    }

    @Override
    public void checkIfUserActive(User user) {
        if (!user.isActive()) {
            throw new UserDeactivatedException();
        }
    }

    @Override
    public boolean isUsersExistIn(Organ organ, Department department, Region region, District district) {
        return 0 != userRepository.count(userSpecifications.withOrganExactly(organ)
                .and(userSpecifications.withDepartmentExactly(department))
                .and(userSpecifications.withRegionExactly(region))
                .and(userSpecifications.withDistrictExactly(district)));
    }

    @Override
    public Organ getUserRelateOrgan(User user) {
        return Optional.ofNullable(user.getOrgan())
                .orElseGet(organService::getMvdOrgan);
    }

}