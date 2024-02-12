package uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.experience.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchMehnatExperiencesParams {

    private Body body;
    @Data
    @AllArgsConstructor
    public static class Body {
        private String pin;
    }
}
