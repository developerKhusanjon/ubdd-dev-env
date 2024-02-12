package uz.ciasev.ubdd_service.mvd_core.expapi.user;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeRequest;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserLoginTimeMapper {

    @Mapping(target = "id", ignore = true)
    UserLoginTime toUserLoginTime(UserLoginTimeRequest request);

    @Mapping(source = "user.username",target = "username")
    @Mapping(source = "user.person.birthAddress.fullAddressText",target = "address")
    UserLoginTimeResponse toUserLoginTimeResponse(UserLoginTime entity);
}
