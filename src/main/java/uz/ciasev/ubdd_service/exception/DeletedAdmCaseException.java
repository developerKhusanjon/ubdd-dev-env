package uz.ciasev.ubdd_service.exception;


public class DeletedAdmCaseException extends ForbiddenException {

    public DeletedAdmCaseException() {
        super(ErrorCode.ADM_CASE_DELETED, "This adm case is deleted");
    }
}
