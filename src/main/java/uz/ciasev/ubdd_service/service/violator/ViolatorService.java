package uz.ciasev.ubdd_service.service.violator;

import uz.ciasev.ubdd_service.dto.internal.request.SingleAddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationArticleRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;

public interface ViolatorService {

    Violator save(Violator violator);

    Violator getOrSave(Violator violator);

    Violator getById(Long id);

    void saveAll(List<Violator> violators);

    List<Violator> findViolatorsByIds(List<Long> ids);

    List<Violator> findByAdmCaseId(Long admCaseId);

    Long findByAdmCaseIdAndPersonId(Long admCaseId, Long personId);

    Violator findSingleByAdmCaseId(Long admCaseId);

    void delete(Violator violator);

    Violator mergeTo(Violator violator, AdmCase toAdmCase);

    Violator updateViolator(User user, Long id, ViolatorRequestDTO requestDTO);

    Violator updateViolatorPostAddress(User user, Long id, SingleAddressRequestDTO requestDTO);

    Violator updateViolatorActualAddress(User user, Long id, SingleAddressRequestDTO requestDTO);

    Violator updateViolatorBirthAddress(User user, Long id, SingleAddressRequestDTO requestDTO);

    void updateEarlierViolatedArticleParts(User user, Long id, List<QualificationArticleRequestDTO> requestDTO);

    void setViolatorInn(Violator violator, String inn);

    void setViolatorPhoto(Violator violator, String photoPath);
}