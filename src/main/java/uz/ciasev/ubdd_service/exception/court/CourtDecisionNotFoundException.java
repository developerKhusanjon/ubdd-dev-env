package uz.ciasev.ubdd_service.exception.court;

public class CourtDecisionNotFoundException extends CourtGeneralException {

    public CourtDecisionNotFoundException(String series, String number) {
        super("Not found decision with series and number : " + series + " " + number);
    }
}
