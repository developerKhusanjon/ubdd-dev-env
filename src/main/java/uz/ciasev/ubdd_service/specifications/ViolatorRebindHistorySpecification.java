package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.history.ViolatorRebindRegistration;
import uz.ciasev.ubdd_service.entity.history.ViolatorRebindRegistration_;

@Component
public class ViolatorRebindHistorySpecification {
    public Specification<ViolatorRebindRegistration> withProtocolId(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(ViolatorRebindRegistration_.protocolId), value);
        };
    }
}
