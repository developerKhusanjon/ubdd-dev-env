package uz.ciasev.ubdd_service.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.user.UserRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.repository.role.RolePermissionRepository;
import uz.ciasev.ubdd_service.repository.user.UserRepository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {

    private final RolePermissionRepository rolePermissionRepository;
    private final ValidationService validationService;
    private final UserRepository userRepository;

    @Override
    public void validate(User admin, Optional<Long> userId, UserRequestDTO userRequestDTO, Supplier<Optional<User>> duplicateUsers) throws ApplicationException {
        ValidationCollectingError errors = new ValidationCollectingError();

        if (duplicateUsers != null) {
            errors.addIf(
                    checkUniqueUsername(duplicateUsers),
                    ErrorCode.DUPLICATE_USERNAME_EXCEPTION
            );
        }

        checkRegionDistrictToAdmin(errors, admin, userRequestDTO);
        checkOrganDepartmentToAdmin(errors, admin, userRequestDTO);
//        checkWorkCertificate(errors, userId, userRequestDTO);

        errors.throwErrorIfNotEmpty();
    }

    @Override
    public void validateRoleList(User admin, List<Role> roles) {
        validateSuperuserRoleAccess(admin, roles);
    }

    private void validateSuperuserRoleAccess(User admin, List<Role> roles) {
        if (!admin.isSuperuser()) {
            roles.stream()
                    .filter(rolePermissionRepository::isSuperuserRole)
                    .findFirst()
                    .ifPresent(superuserRole -> {
                        throw new NoAccessOnSuperuserRoleException(admin, superuserRole);
                    });
        }

    }

    private boolean checkUniqueUsername(Supplier<Optional<User>> f) {
        Optional<User> duplicate = f.get();
        if (duplicate.isPresent()) {
            return true;
        }
        return false;
    }

    private void checkWorkCertificate(ValidationCollectingError errors, Optional<Long> updatedUserId, UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getOrgan() == null) {
            return;
        }

        if (!userRequestDTO.getOrgan().isGai()) {
            return;
        }

        if (!userRequestDTO.getStatus().getIsUserActive()) {
            return;
        }

        Optional<User> existsUser;
        try {
            existsUser = userRepository.findByGaiWorkCertificate(userRequestDTO.getWorkCertificate());
        } catch (IncorrectResultSizeDataAccessException e) {
            errors.add(ErrorCode.DUPLICATE_WORK_CERTIFICATE_ERROR);
            return;
        }

        if (existsUser.isEmpty()) {
            return;
        }

        if (updatedUserId.isEmpty()) {
            errors.add(ErrorCode.DUPLICATE_WORK_CERTIFICATE_ERROR);
            return;
        }

        Long existsUserId = existsUser.get().getId();
        Long userId = updatedUserId.get();
        if (!existsUserId.equals(userId)) {
            errors.add(ErrorCode.DUPLICATE_WORK_CERTIFICATE_ERROR);
        }
    }

    private void checkRegionDistrictToAdmin(ValidationCollectingError errors, User admin, UserRequestDTO userRequestDTO) {
        errors.addIf(
                validationService.checkRegionNotInUser(admin, userRequestDTO.getRegion()),
                ErrorCode.USER_REGION_NOT_AVAILABLE_FOR_ADMIN,
                String.format("Admin user(%s) with regionId=%s has no access on regionId=%s", admin.getId(), admin.getRegionId(), Optional.ofNullable(userRequestDTO.getRegion()).map(Region::getId).orElse(null))
        );

        errors.addIf(
                validationService.checkDistrictNotInUser(admin, userRequestDTO.getDistrict()),
                ErrorCode.USER_DISTRICT_NOT_AVAILABLE_FOR_ADMIN,
                String.format("Admin user(%s) with districtId=%s has no access on districtId=%s", admin.getId(), admin.getDistrictId(), Optional.ofNullable(userRequestDTO.getDistrict()).map(District::getId).orElse(null))
        );
    }

    private void checkOrganDepartmentToAdmin(ValidationCollectingError errors, User admin, UserRequestDTO userRequestDTO) {
        if (admin.getOrgan() != null) {
            errors.addIf(
                    !admin.getOrgan().equals(userRequestDTO.getOrgan()),
                    ErrorCode.USER_ORGAN_NOT_AVAILABLE_FOR_ADMIN,
                    String.format("Admin user(%s) with organId=%s has no access on organId=%s", admin.getId(), admin.getOrgan(), Optional.ofNullable(userRequestDTO.getOrgan()).map(Organ::getId).orElse(null))
            );
            if (admin.getDepartment() != null) {
                errors.addIf(
                        !admin.getDepartment().equals(userRequestDTO.getDepartment()),
                        ErrorCode.USER_DEPARTMENT_NOT_AVAILABLE_FOR_ADMIN,
                        String.format("Admin user(%s) with departmentId=%s has no access on departmentId=%s", admin.getId(), admin.getDepartmentId(), Optional.ofNullable(userRequestDTO.getDepartment()).map(Department::getId).orElse(null))
                );
            }
        }
    }

    @Override
    public void validateUserUniqueness(Organ organ, String pinpp) {

        if (organ == null) {
            return;
        }

        long numberOfUserLimit = organ.getMaxAccountsPerUser();

        long numberOfExistedUsers = userRepository.countUsersByPinpp(organ.getId(), pinpp).orElse(0L);

        if (numberOfExistedUsers >= numberOfUserLimit) {
            throw new UserLimitByPinppExceeded(organ, numberOfUserLimit, numberOfExistedUsers, pinpp);
        }
    }

    @Override
    public void validateAdminAccessOnUser(User admin, User user) {
        boolean noAccess = validationService.checkUserHaveNoAccess(admin, user.getRegionId(), user.getDistrictId(), user.getOrganId(), user.getDepartmentId());
        if (noAccess) {
            throw new NoAccessOnObjectException(admin, user);
        }
    }

    @Override
    public void checkAdministrationAllow(@Nullable Organ organ) {
        if (organ == null) return;
        if (organ.getIsAdministrationBlocked()) throw new ValidationException("ADMINISTRATION_OF_ORGAN_BLOCKED", String.format("Administration of organ(%s) users blocked by system admin", organ.getId()));
    }


}
