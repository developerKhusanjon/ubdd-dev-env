package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

public class MixingOfCourtAndOrganArticlesUnacceptable extends ApplicationException {

    public MixingOfCourtAndOrganArticlesUnacceptable() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.MIXING_OF_ORGAN_AND_COURT_ARTICLES_UNACCEPTABLE);
    }
}
