package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment;
import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment_;

@Component
public class CourtExecutionPaymentSpecification {

    public Specification<CourtExecutionPayment> withIsSent(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(CourtExecutionPayment_.isSent), value);
        };
    }

    public Specification<CourtExecutionPayment> withIsAccept(Boolean value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(CourtExecutionPayment_.isAccept), value);
        };
    }

}
