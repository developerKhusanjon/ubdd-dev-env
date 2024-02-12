package uz.ciasev.ubdd_service.mvd_core.expapi.user;

import lombok.*;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_login_time")
public class UserLoginTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;
}
