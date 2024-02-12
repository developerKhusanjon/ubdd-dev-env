package uz.ciasev.ubdd_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.entity.AdmCaseChangeReason;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.admcase.AdmCaseChangeReasonRepository;

@Service
@RequiredArgsConstructor
public class AdmCaseChangeReasonServiceImpl implements AdmCaseChangeReasonService {

    private final AdmCaseChangeReasonRepository admCaseChangeReasonRepository;

    @Override
    public AdmCaseChangeReason create(AdmCaseChangeReason admCaseChangeReason) {
        return admCaseChangeReasonRepository.save(admCaseChangeReason);
    }

    @Override
    public AdmCaseChangeReason create(User user, AdmCase admCase, AdmEntity entity, ChangeReasonRequestDTO changeReasonRequestDTO) {
        AdmCaseChangeReason admCaseChangeReason = changeReasonRequestDTO.buildAdmCaseChangeReason();

        admCaseChangeReason.setUser(user);
        admCaseChangeReason.setAdmCase(admCase);
        admCaseChangeReason.setEntityId(entity.getId());
        admCaseChangeReason.setEntityName(entity.getEntityNameAlias());

        return create(admCaseChangeReason);
    }
}
