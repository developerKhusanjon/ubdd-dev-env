package uz.ciasev.ubdd_service.entity.permission;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.utils.converter.PermissionAliasConverter;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString(of = "id")
@Entity
@Table(name = "permission")
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_seq")
    @SequenceGenerator(name = "permission_id_seq", sequenceName = "permission_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

//    @Enumerated(EnumType.STRING)
    @Convert(converter = PermissionAliasConverter.class)
    private PermissionAlias alias;

    private String description;

    private Boolean isSuperuserPermission;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;

    @ManyToOne
    @JoinColumn(name = "permission_type_id")
    private PermissionType permissionType;

    private String service;

//    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, orphanRemoval = true)
//    private Set<RolePermission> rolePermissions = new HashSet<>();

    public Permission(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Permission that = (Permission) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
