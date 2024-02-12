package uz.ciasev.ubdd_service.repository.dict.resolution;

import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.List;

public interface TerminationReasonRepository extends AbstractDictRepository<TerminationReason> {

    List<TerminationReason> findAllByIsParticipateOfRepeatability(Boolean isParticipateOfRepeatability);
}
