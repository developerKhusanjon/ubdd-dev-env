package uz.ciasev.ubdd_service.entity.prosecutor;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractProsecutorDocument implements AdmEntity, Serializable {

    @CreatedDate
    @Setter
    @Getter
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Setter
    @Getter
    private String uri;

    public AbstractProsecutorDocument(User user, String uri) {
        this.user = user;
        this.uri = uri;
    }

    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    public Long getUserId() {
        if (this.user == null) {
            return null;
        }
        return this.user.getId();
    }
}
