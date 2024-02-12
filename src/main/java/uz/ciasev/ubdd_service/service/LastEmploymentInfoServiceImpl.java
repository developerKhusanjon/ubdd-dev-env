package uz.ciasev.ubdd_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.entity.LastEmploymentInfo;
import uz.ciasev.ubdd_service.entity.LastEmploymentInfoOwner;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.LastEmploymentInfoRepository;

@Service
@RequiredArgsConstructor
public class LastEmploymentInfoServiceImpl implements LastEmploymentInfoService {
    private final LastEmploymentInfoRepository repository;

    @Override
    @Transactional
    public void addLastEmploymentInfoToOwner(User user,
                                             LastEmploymentInfoOwner owner,
                                             LastEmploymentInfoDTO lastEmploymentInfoDTO) {
        deleteLastEmploymentInfoById(owner.getLastEmploymentInfoId());
        if (lastEmploymentInfoDTO == null || lastEmploymentInfoDTO.isEmpty()) {
            owner.setLastEmploymentInfo(null);
        } else {
            owner.setLastEmploymentInfo(createLastEmploymentInfo(user, lastEmploymentInfoDTO));
        }
    }

    @Override
    public LastEmploymentInfoDTO getDTOByOwner(LastEmploymentInfoOwner owner) {
        if (owner.getLastEmploymentInfoId() == null) {
            return new LastEmploymentInfoDTO();
        }

        LastEmploymentInfo lastEmploymentInfo = getById(owner.getLastEmploymentInfoId());
        return new LastEmploymentInfoDTO(lastEmploymentInfo);
    }

    private LastEmploymentInfo getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(LastEmploymentInfo.class, id));
    }

    private LastEmploymentInfo createLastEmploymentInfo(User user, LastEmploymentInfoDTO lastEmploymentInfoDTO) {
        return repository.save(lastEmploymentInfoDTO.buildWith(user));
    }

    private void deleteLastEmploymentInfoById(Long id) {
        if (id != null) {
            repository.deleteById(id);
        }
    }
}
