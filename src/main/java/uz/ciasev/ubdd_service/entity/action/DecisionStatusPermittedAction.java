package uz.ciasev.ubdd_service.entity.action;

import lombok.*;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "decision_status_permitted_action")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class DecisionStatusPermittedAction implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_status_id")
    AdmStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "action_id")
    Action action;

    @Getter
    private Boolean considererOnly;

    //    JPA AND CRITERIA FIELDS

    @Column(name = "adm_status_id", insertable = false, updatable = false)
    private Long statusId;

    @Column(name = "action_id", insertable = false, updatable = false)
    private Long actionId;

    public Long getStatusId() {
        return status.getId();
    }

    public Long getActionId() {
        return action.getId();
    }
}
