package uz.ciasev.ubdd_service.utils.deserializer;

import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.service.role.RoleService;

public class RoleDeserializer extends AbstractEntityDeserializer<Role> {

    public RoleDeserializer(RoleService service) {
        super(Role.class, service::getById);
    }
}
