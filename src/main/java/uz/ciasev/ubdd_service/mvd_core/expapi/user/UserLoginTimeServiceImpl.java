package uz.ciasev.ubdd_service.mvd_core.expapi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeRequest;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeResponse;
import uz.ciasev.ubdd_service.repository.expapi.UserLoginTimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoginTimeServiceImpl implements UserLoginTimeService {


    private final UserLoginTimeMapper mapper;

    private final UserLoginTimeRepository repository;

    @Override
    public void save(UserLoginTimeRequest request) {

        var reponse = repository.save(mapper.toUserLoginTime(request));
        var userList = getByUserId(reponse.getUser().getId());
        if (userList.size() > 10)
            repository.deleteById(userList.get(0).getId());
    }

    @Override
    public Page<UserLoginTimeResponse> findAllByUsername(String username, Pageable pageable) {
        return repository.findAllByUserUsername(username,pageable);
    }

    @Override
    public List<UserLoginTime> getByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
