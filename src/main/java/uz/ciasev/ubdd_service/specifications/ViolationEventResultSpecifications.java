package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventResult;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventResult_;

@Component
public class ViolationEventResultSpecifications {

    public Specification<ViolationEventResult> withIsActive(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get(ViolationEventResult_.isActive),
                    value
            );
        };
    }

    public Specification<ViolationEventResult> withViolationEventId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get(ViolationEventResult_.violationEventId),
                    value
            );
        };
    }
}
