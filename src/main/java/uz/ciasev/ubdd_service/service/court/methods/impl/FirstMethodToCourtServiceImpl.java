package uz.ciasev.ubdd_service.service.court.methods;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtApiService;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtSendResult;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationTypeAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.document.DocumentRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.aop.single_thread_operation.SingleThreadOperation;
import uz.ciasev.ubdd_service.service.aop.single_thread_operation.SingleThreadOperationResource;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;

import java.util.List;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias.*;
import static uz.ciasev.ubdd_service.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirstMethodToCourtServiceImpl implements FirstMethodToCourtService {

    private final AdmCaseService admCaseService;
    private final CourtHandlingResponseService handlingResponseService;
    private final CourtSendDTOService courtSendDTOService;
    private final DecisionService decisionService;
    private final ResolutionService resolutionService;
    private final CourtApiService courtApiService;
    private final DocumentRepository documentRepository;

    /**
     * First method (Send adm case to court)
     */
    @Override
    @SingleThreadOperation(type = SingleThreadOperationTypeAlias.ADM_CASE_TO_COURT)
    @DigitalSignatureCheck(event = SignatureEvent.SEND_TO_COURT)
    public AdmCase sendAdmCaseToCourt(User user, @SingleThreadOperationResource Long id) {

        var admCase = admCaseService.getById(id);
        validate(admCase);

        CourtRequestDTO<FirstCourtAdmCaseRequestDTO> requestDTO = courtSendDTOService.buildFirstMethod(admCase);
        CourtSendResult sendResult = courtApiService.sendAdmCase(requestDTO);
        try {
            handlingResponseService.handleResponse(sendResult.getResponse(), id, false, true);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            log.error("SEND CASE {} TO COURT ERROR: ", id, e);
            throw e;
        }

        return admCase;
    }

    /**
     * First method (Send adm case to court by 308 prime 1)
     * Перед отправкой в суд по 308 статье следует сделать след проверки:
     * 1.  Проверить признак 308
     * 2.  Проверить заполненность полей суда
     * 3.  Проверить наличие в деле только 1 протокола
     * 4.  Проверить, что тип решения это ШТРАФ
     */
    @Override
    @SingleThreadOperation(type = SingleThreadOperationTypeAlias.ADM_CASE_TO_COURT)
    @DigitalSignatureCheck(event = SignatureEvent.SEND_TO_COURT)
    public AdmCase sendAdmCaseToCourt308(User user, @SingleThreadOperationResource Long id) {

        var admCase = admCaseService.getById(id);
        validate308(admCase);

        CourtRequestDTO<FirstCourtAdmCaseRequestDTO> requestDTO = courtSendDTOService.buildFirstMethod(admCase);
        CourtSendResult sendResult = courtApiService.sendAdmCase(requestDTO);
        try {
            handlingResponseService.handleResponse(sendResult.getResponse(), id, true, true);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            log.error("SEND 308 CASE {} TO COURT ERROR: ", id, e);
            throw e;
        }

        return admCase;
    }

    private void validate(AdmCase admCase) {
        if (admCase.getCourtOutNumber() == null) {
            throw new ValidationException(ErrorCode.COURT_FIELDS_REQUIRED);
        }

        validateFiles(admCase);
    }

    private void validateFiles(AdmCase admCase) {
//        var hasProtocol = false;
//        var hasPersonCard = false;
        var hasPichalTrebovaniya = false;

        var filesTuple = documentRepository.findAllCourtProjectionByAdmCaseId(admCase.getId());

        for (var file : filesTuple) {
            var documentTypeAlias = file.getDocumentTypeAlias();

//            if (documentTypeAlias.equals(PROTOCOl))
//                hasProtocol = true;
//
//            if (documentTypeAlias.equals(PERSON_CARD))
//                hasPersonCard = true;

            if (documentTypeAlias.equals(PICHAL_TREBOVANIYA))
                hasPichalTrebovaniya = true;
        }

        ValidationCollectingError error = new ValidationCollectingError();

//        error.addIf(!hasProtocol, COURT_PROTOCOL_PDF_REQUIRED);
//        error.addIf(!hasPersonCard, COURT_PERSON_CARD_PDF_REQUIRED);
        error.addIf(!hasPichalTrebovaniya, COURT_PICHAL_TREBOVANIYA_REQUIRED);

        error.throwErrorIfNotEmpty();
    }

    /**
     * Валидация при отправке в суд по 308 прим
     * b. Если в электронных документах дела нет PDF-карора, тогда его надо сформировать и включить в список передаваемых ссылок
     * c. Среди электронных документов должен быть документ с одним из типов (22, 21)
     * d. Поле admissionType = 5, если нет, тогда сообщение пользователю и не отправлять в суд
     * e. Должно быть решение = штраф
     * f. Протокол в АДМ деле должен быть один
     * g. Изменить статус АДМ дела на «Передан в суд»
     * h. Статус активного решения (не отмененного) перевести в "Исполнение не возможно" или "Отменено судом" (новый статус для решения).
     * Если решение уже "Исполнено" или "Частично исполнено", тогда  статус нужно сбросить в историю, т.е. чтобы можно было посмотреть
     * <p>
     * 1. Значение поля hasDecision = True
     * 2. При генерации квитанции  – оплата должна пойти органу (по реализованному алгоритму в departments так и должно быть,т.е. укладқвается в общую схему)
     * 3. Дальнейшая обработка в суде – как обычное АДМ дело суда (все алгоритмы и правила остаются без изменений)
     **/
    private void validate308(AdmCase admCase) {
        validateCourtDeclarationFields(admCase);
        validate308Files(admCase);
        validateSingleDecision(admCase);
        validateAdmissionType(admCase);
    }

    private void validateAdmissionType(AdmCase admCase) {

        Long admissionType = admCase.getCourtConsideringBasisId();
        if (!admissionType.equals(5L))
            throw new ValidationException(COURT_308_CONSIDERING_BASIS_SHOULD_BE_FIVE);
    }

    private void validateSingleDecision(AdmCase admCase) {
        Optional<Resolution> resolutionOptional = resolutionService.findActiveByAdmCaseId(admCase.getId());
        if (resolutionOptional.isEmpty())
            throw new ValidationException(COURT_308_HAS_NOT_RESOLUTION);

        var resolution = resolutionOptional.get();
        List<Decision> decisions = decisionService.findByResolutionId(resolution.getId());
        if (decisions == null || decisions.size() != 1)
            throw new ValidationException(COURT_308_HAS_MORE_ONE_DECISIONS);

        var decision = decisions.get(0);
        if (decision.getMainPunishment() == null || decision.getMainPunishment().getPenalty() == null)
            throw new ValidationException(COURT_308_HAS_NOT_PENALTY);
    }

    private void validateCourtDeclarationFields(AdmCase admCase) {
        if (admCase.getCourtOutNumber() == null)
            throw new ValidationException(COURT_FIELDS_REQUIRED);
    }

    private void validate308Files(AdmCase admCase) {
//        var hasKaror = false;
        var has308Documents = false;

        var filesTuple = documentRepository.findAllCourtProjectionByAdmCaseId(admCase.getId());

        for (var file : filesTuple) {
            var documentTypeAlias = file.getDocumentTypeAlias();

            if (documentTypeAlias.equals(POOR_PERSON) || documentTypeAlias.equals(INSIGNIFICANT))
                has308Documents = true;

//            if (documentTypeAlias.equals(DECISION))
//                hasKaror = true;
        }

        ValidationCollectingError error = new ValidationCollectingError();

        error.addIf(!has308Documents, COURT_308_HAS_NOT_SPECIFIED_DOCUMENTS);
//        error.addIf(!hasKaror, COURT_308_KAROR_REQUIRED);

        error.throwErrorIfNotEmpty();
    }
}
