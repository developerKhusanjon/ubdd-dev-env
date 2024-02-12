package uz.ciasev.ubdd_service.entity.role;

import lombok.*;
import uz.ciasev.ubdd_service.entity.permission.Permission;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_permission")
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Role role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "permission_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Permission permission;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    @Column(name = "permission_id", insertable = false, updatable = false)
    private Long permissionId;

    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }
}
