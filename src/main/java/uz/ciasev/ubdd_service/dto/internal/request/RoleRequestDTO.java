package uz.ciasev.ubdd_service.dto.internal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidMultiLanguage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDTO {

    @Valid
    @ValidMultiLanguage
    private MultiLanguage name;
    @Size(min = 1, message = "PERMISSIONS_REQUIRED")
    @NotNull(message = "PERMISSIONS_REQUIRED")
    private Set<Long> permissions;

    public Role buildRole() {
        Role role = new Role();
        role.setName(this.name);

        return role;
    }
}
