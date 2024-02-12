package uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.experience.response;

import lombok.Data;

import java.util.List;

@Data
public class MehnatExperiencesData {

    private List<MehnatExperience> experiences;
    private MehnatProfile profile;

}
