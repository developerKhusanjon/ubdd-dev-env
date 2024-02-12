package uz.ciasev.ubdd_service.entity.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.user.UserRole;
import uz.ciasev.ubdd_service.utils.deserializer.RoleDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonDeserialize(using = RoleDeserializer.class)
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage name;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RolePermission> rolePermissions = new HashSet<>();

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserRole> userRoles = new HashSet<>();

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, MultiLanguage name, LocalDateTime createdTime) {
        this.id = id;
        this.name = name;
        this.createdTime = createdTime;
    }
}
