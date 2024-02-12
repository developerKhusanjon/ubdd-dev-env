package uz.ciasev.ubdd_service.entity.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserRole implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_seq")
    @SequenceGenerator(name = "user_role_id_seq", sequenceName = "user_role_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", updatable = false)
    User adminUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", updatable = false)
    Role role;


    // JPA AND CRITERIA FIELDS

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    public UserRole(User admin, User user, Role role) {
        this.adminUser = admin;
        this.user = user;
        this.role = role;
    }

    public Long getAdminUserId() {
        if (adminUser == null) return null;
        return adminUser.getId();
    }

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    public Long getRoleId() {
        if (role == null) return null;
        return role.getId();
    }

}
