package uz.ciasev.ubdd_service.mvd_core.experiencesapi.mapper;

import org.mapstruct.Mapper;
import uz.ciasev.ubdd_service.mvd_core.experiencesapi.dto.ExperienceDto;
import uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.experience.response.MehnatExperience;

@Mapper(componentModel = "spring")
public interface CitizenExperienceMapper {
    ExperienceDto toExperienceDto(MehnatExperience experience);

}
