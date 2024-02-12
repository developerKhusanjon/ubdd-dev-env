package uz.ciasev.ubdd_service.entity.action;

import lombok.*;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "adm_status_permitted_action")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class AdmStatusPermittedAction implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_status_id")
    private AdmStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "action_id")
    private Action action;

    @Getter
    private Boolean considererOnly = true;

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
