package uz.ciasev.ubdd_service.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_user_role_view")
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleView {

    @Id
    @Getter
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Getter
    @Column(name = "roles", insertable = false, updatable = false)
    private String roles;
}
