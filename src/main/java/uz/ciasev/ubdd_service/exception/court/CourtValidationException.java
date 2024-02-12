package uz.ciasev.ubdd_service.exception.court;

public class CourtValidationException extends CourtGeneralException {
    public static final String MATERIAL_BASE_REQUIRED = "materialPreviousClaimId or Resolution series and number required";
    public static final String MATERIAL_BASE_AMBIGUOUS = "Send only materialPreviousClaimId or Resolution series and number";
    public static final String MATERIAL_BASE_NOT_ACTIVE_AND_CASE_ALREADY_HAS_COURT_RESOLUTION = "Requested Resolution is not active. And adm case already has active court resolution";
    public static final String MATERIAL_BASE_NOT_ACTIVE = "Resolution is not active";
    public static final String MATERIAL_VIOLATOR_NOT_SINGLE = "Send exactly one violator for mvd resolution";
    public static final String NOT_CONSISTENT_ADM_CASE_ID = "Resolution and case not consist";
    public static final String NOT_CONSISTENT_VIOLATOR_ID = "Resolution and violator not consist";

    public static final String CASE_ID_REQUIRED = "Case ID field required";
    public static final String REQUEST_BODY_REQUIRED = "Request body required";
    public static final String STATUS_REQUIRED = "Status field required";
    public static final String CLAIM_ID_REQUIRED = "Claim ID field required";
    public static final String IS_PAUSED_TRUE = "Is Paused field should be equals true";
    public static final String IS_PAUSED_FALSE = "Is Paused field should be equals false";
    public static final String COURT_FIELD_NOT_FOUND = "Court not found";
    public static final String COURT_FIELD_REQUIRED = "Court field required";
    public static final String MATERIAL_TYPE_FIELD_REQUIRED = "MaterialType field required";
    public static final String TRANSFER_AND_MERGE_IDS_ERROR = "Fields otherCourtClaimId and caseMergeId both filled";
    public static final String MATERIAL_NOT_HANDLE = "Could not handleInMultiThread material";

    public static final String COURT_STATUS_AND_FINAL_RESULT_NOT_CONSISTENT = "For adm case with status 17 and 18 finalResult should be equals 4";
    public static final String REGISTRATION_DECLINED_FIELDS = "Declined date and reasons required";
    public static final String DEFENDANT_IS_EMPTY = "Defendant block is empty ";
    public static final String FINAL_RESULT_MAST_BE_4 = "Final result mast be 4 for returning";
    public static final String FINAL_RESULT_MAST_BE_4_OR_217_OR_218 = "Final result mast be 4, 217, 218 for returning 2 instance";
    public static final String COURT_EVIDENCE_CATEGORY_REQUIRED = "Court evidence category empty for claimId";
    public static final String COURT_EVIDENCE_MEASURE_AND_UNITY_REQUIRED = "Court evidence measure  or countAndUnity empty for claimId";
    public static final String COURT_EVIDENCE_CURRENCY_AND_AMOUNT_REQUIRED = "Court evidence currency or amount empty for claimId";
    public static final String COURT_EVIDENCE_RESULT_REQUIRED = "Court evidence result empty for claimId";
    public static final String FAVOR_TYPE_NOT_SUPPORTED = "Favor type and from whom values not supported ";
    public static final String FINAL_RESULT_REQUIRED = "Final result required";
    public static final String FINAL_RESULT_4_AND_STATUS_13 = "Final result(4) and status(13) are not consistent";
    public static final String FINAL_RESULT_115_AND_STATUS_13 = "Final result(115) and status(13) are not consistent";
    public static final String RETURN_REASON_MULTIPLY = "Return reason has many different value";
    public static final String RETURN_REASON_NOT_SET = "Return reason missing";
    public static final String RETURN_REASON_WITHOUT_RETURN_STATUS = "Return reason set for not RETURN status";
    public static final String MIB_TYPE_CANT_BE_PROCESSED = "permitted type can't be processed";
    public static final String COURT_PUNISHMENT_ADDITIONAL = "Court additional punishment not found";
    public static final String REGISTRATION_FIELDS = "Registration date and number required";
    public static final String COURT_RESOLUTION_NOT_FOUND = "Court resolution not found with ";
    public static final String RETREAL_COURT_EXCEPTION = "Canceling reason should be equals 2 for all defendants";
    public static final String RETURN_REASON_REQUIRED = "Return reason can't be empty";
    public static final String STATUS_CODE_UNDEFINED = "Undefined status for this method : ";
    public static final String COURT_SEPARATION_AND_MERGING_BY_308_NOT_ALLOWED = "Separation and merging not allowed by 308";
    public static final String COURT_END_BASE_REQUIRED_FOR_TERMINATION = "End base required for termination";
    public static final String COURT_CASSATION_ADDITIONAL_RESULT_REQUIRED = "Cassation additional result required";
    public static final String ADM_CASE_ALREADY_MERGED_OR_RETURNED = "Adm case has already status like merged or returned";
    public static final String COURT_CANCELING_REASON_REQUIRED = "Canceling reason field required";
    public static final String COURT_CHANGING_REASON_REQUIRED = "Changing reason field required";
    public static final String COURT_FINAL_RESULT_NOT_SUPPORTED = "Court final result not supported";
    public static final String FOREIGN_CASE_VIOLATORS = "Defendant list contain person not present in adm case";

    public static final String IS_GRANTED_REQUIRED_FOR_MATERIAL_RESOLUTION = "Field isGranted required for status 13";
    public static final String REJECT_BASE_REQUIRED_FOR_REJECTED_MATERIAL_RESOLUTION = "Field materialRejectBase required for isGranted = False and status 13";
    public static final String FINAL_RESULT_MAST_BE_REJECT_OR_EMPTY_FOR_REJECTED_MATERIAL_RESOLUTION = "Field finalResult or material mast be null or 112 for isGranted = False and status 13";
    public static final String END_BASE_REQUIRED = "Field endBase required for ending(termination) resolution";
    public static final String MAIN_PUNISHMENT_TYPE_REQUIRED_RESOLUTION = "Field mainPunishmentType required";
    public static final String OTHER_COURT_MOVEMENT_DATA_NOT_VALID = "Field otherCourt and otherCourtClaimId mast agree";
    public static final String RE_QUALIFICATION_NOT_ALLOW_FOR_DEFENDANTS = "finalResult 115 unacceptable for defendant";
    public static final String APPEAL_RESULT_UNKNOWN = "Fields mainPunishment or endBase required  for cassation result";
    public static final String APPEAL_RESULT_NOT_CLEAR = "Fields mainPunishment and endBase can not be present together for cassation result";
    public static final String FINE_AMOUNT_REQUIRED = "fineTotal field required";
    public static final String PUNISHMENT_EVIDENCES_REQUIRED = "withdrawal/confiscation Evidences field required";
    public static final String ARREST_DURATION_REQUIRED = "arrest field required";
    public static final String PUNISHMENT_DURATION_REQUIRED = "punishment field required";
    public static final String PUNISHMENT_TYPE_NOT_ALLOWED_AS_ADDITION = "Penalty, arrest and deportation unacceptable for addition punishment";
    public static final String COMPENSATION_AMOUNT_REQUIRED = "exactedDamageTotal field required";
    public static final String COMPENSATION_CURRENCY_REQUIRED = "exactedDamageCurrency field required";
    public static final String COMPENSATION_PAYER_TYPE_REQUIRED = "fromWhom field required";
    public static final String COMPENSATION_VICTIM_TYPE_REQUIRED = "inFavorType field required";
    public static final String EVIDENCE_COURT_ID_REQUIRED = "evidenceCourtId field required";
    public static final String EVIDENCE_DECISIONS_REQUIRED_FOR_CALCULATE_PUNISHMENT_AMOUNT = "Evidence decision list required for confiscation/withdrawal";
    public static final String PUNISHMENT_EVIDENCE_NOT_PRESENT_IN_EVIDENCE_LIST = "Confiscated/withdrawn evidence id not present in evidence decision list";

    public CourtValidationException(String message) {
        super(message);
    }
}
