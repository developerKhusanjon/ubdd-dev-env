package uz.ciasev.ubdd_service.mvd_core.api.court;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.eight.EighthMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.five.FifthMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.mapper.CourtRequestMapperFacade;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.CourtSecondMaterialMethodService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.CourtThreeMaterialMethodService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.nine.NineMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.seven.SevenMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.three.ThirdMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.two.SecondMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResultDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.eight.EightCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth.FiveCourtMibDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth.FiveCourtPerformancesRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.material.CourtMaterialRegistrationRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestExternalDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh.CourtDecisionFileRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtDefendantRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.court.CourtLog;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ResolutionInAdmCaseAlreadyExists;
import uz.ciasev.ubdd_service.exception.court.CourtResult;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.exception.court.CourtWrappedException;
import uz.ciasev.ubdd_service.exception.court.ExternalException;
import uz.ciasev.ubdd_service.service.court.CourtLogService;
import uz.ciasev.ubdd_service.service.victim.VictimService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourtWebhookServiceImpl implements CourtWebhookService {

    private final CourtLogService courtLogService;
    private final SecondMethodFromCourtService secondMethodFromCourtService;
    private final ThirdMethodFromCourtService thirdMethodFromCourtService;
    private final FifthMethodFromCourtService fifthMethodFromCourtService;
    private final SevenMethodFromCourtService sevenMethodFromCourtService;
    private final EighthMethodFromCourtService eighthMethodFromCourtService;
    private final NineMethodFromCourtService nineMethodFromCourtService;
    private final CourtSecondMaterialMethodService secondMaterialMethodFromCourtService;
    private final CourtThreeMaterialMethodService threeMaterialMethodService;
    private final CheckCourtDuplicateRequestService courtDuplicateRequestService;
    private final CourtRequestMapperFacade requestMapper;

    private final ViolatorService violatorService;
    private final VictimService victimService;

    @Override
    public CourtResponseDTO acceptSecondMethod(CourtRequestDTO<CourtRegistrationStatusRequestDTO> requestDTO) {
        CourtLog courtLog = courtLogService.save(requestDTO.getSendDocumentRequest(), COURT_SECOND);

        try {
            secondMethodFromCourtService.accept(requestDTO.getSendDocumentRequest());
            return buildCourtResponse(courtLog.getId());
        } catch (ExternalException e) {
            e.setEnvelopeId(courtLog.getId());
            throw e;
        } catch (ApplicationException e) {
            throw new CourtWrappedException(e, courtLog.getId());
        }
    }

    @Override
    public CourtResponseDTO acceptThirdMethod(CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO) {

        replacePersonIdByViolatorId(requestDTO);

        CourtLog courtLog = courtLogService.save(requestDTO.getSendDocumentRequest(), COURT_THIRD);
        if (requestDTO.getSendDocumentRequest().isMaterial()) {
            ThirdCourtRequest mappedRequest = requestMapper.map(requestDTO.getSendDocumentRequest());
            threeMaterialMethodService.accept(mappedRequest);
        } else {

            try {
                thirdMethodFromCourtService.accept(requestDTO.getSendDocumentRequest());
            } catch (ResolutionInAdmCaseAlreadyExists e) {
                // Сохранене хэша запросов добавилось не сразу, и в очереди суда осталос много дублирующихся запрсосов.
                // Повторное вынесение решение предотварщаеться контрейнтом в базе, но это лишная нагрузка на сервре.
                // Этот кода, кохраняет в базу хэш запроса от суда, в том случае, если вылетела ошибка нарушения констрйнта резолюции в деле, и отменила транзакцию
                // для того что бы в следующий раз запрос обрубался проверкой хэша.
                courtDuplicateRequestService.checkAndRemember(requestDTO.getSendDocumentRequest());
                throw e;
            } catch (ExternalException e) {
                e.setEnvelopeId(courtLog.getId());
                throw e;
            } catch (ApplicationException e) {
                throw new CourtWrappedException(e, courtLog.getId());
            }
        }
        return buildCourtResponse(courtLog.getId());
    }

    @Override
    public CourtResponseDTO acceptFiveMethod(CourtRequestDTO<FiveCourtMibDTO> requestDTO) {

        Long caseId = requestDTO.getSendDocumentRequest().getCaseId();
        List<FiveCourtPerformancesRequestDTO> performances = requestDTO.getSendDocumentRequest().getPerformanceList();

        performances.forEach(performance -> {
            Long personId = performance.getViolatorId();
            if (personId != null) {
                performance.setViolatorId(violatorService.findByAdmCaseIdAndPersonId(caseId, personId));

            }
        });


        CourtLog courtLog = courtLogService.save(requestDTO.getSendDocumentRequest(), COURT_FIFTH);

        try {
            fifthMethodFromCourtService.accept(requestDTO.getSendDocumentRequest());
            return buildCourtResponse(courtLog.getId());
        } catch (ExternalException e) {
            e.setEnvelopeId(courtLog.getId());
            throw e;
        } catch (ApplicationException e) {
            throw new CourtWrappedException(e, courtLog.getId());
        }
    }

    @Override
    public CourtResponseDTO acceptSevenMethod(CourtRequestDTO<CourtDecisionFileRequestDTO> requestDTO) {
        CourtLog courtLog = courtLogService.save(requestDTO.getSendDocumentRequest(), COURT_SEVEN);
        try {
            sevenMethodFromCourtService.accept(requestDTO);
            return buildCourtResponse(courtLog.getId());
        } catch (ExternalException e) {
            e.setEnvelopeId(courtLog.getId());
            throw e;
        } catch (ApplicationException e) {
            throw new CourtWrappedException(e, courtLog.getId());
        }
    }

    @Override
    public CourtRequestDTO<EightCourtResolutionRequestDTO> acceptEightMethod(String series, String number) {
        return eighthMethodFromCourtService.sendResolution(series, number);
    }

    @Override
    public CourtResponseDTO acceptNineMethod(CourtRequestDTO<CourtVictimRequestExternalDTO> requestDTO) {
        CourtLog courtLog = courtLogService.save(requestDTO.getSendDocumentRequest(), COURT_NINE);

        CourtVictimRequestDTO mappedRequest = requestMapper.map(requestDTO.getSendDocumentRequest());

        try {
            Person victim = nineMethodFromCourtService.accept(mappedRequest);
            return buildCourtResponse(Optional.ofNullable(victim).map(Person::getId).orElse(null));
        } catch (ExternalException e) {
            e.setEnvelopeId(courtLog.getId());
            throw e;
        } catch (ApplicationException e) {
            throw new CourtWrappedException(e, courtLog.getId());
        }
    }

    @Override
    public CourtResponseDTO acceptSecondMaterialMethod(CourtRequestDTO<CourtMaterialRegistrationRequestDTO> requestDTO) {

        CourtLog courtLog = courtLogService.save(requestDTO.getSendDocumentRequest(), COURT_SECOND_MATERIAL);
        try {
            CourtMaterial courtMaterial = secondMaterialMethodFromCourtService.accept(requestDTO.getSendDocumentRequest());
            return buildCourtResponse(courtMaterial.getId());
        } catch (ExternalException e) {
            e.setEnvelopeId(courtLog.getId());
            throw e;
        } catch (ApplicationException e) {
            throw new CourtWrappedException(e, courtLog.getId());
        }
    }

    private CourtResponseDTO buildCourtResponse(Long envelopeId) {
        return new CourtResponseDTO(
                new CourtResultDTO(CourtResult.SUCCESSFULLY, "Ok"),
                envelopeId
        );
    }

    private void replacePersonIdByViolatorId(CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO) {
        Long caseId = requestDTO.getSendDocumentRequest().getCaseId();
        List<ThirdCourtDefendantRequestDTO> defendants = requestDTO.getSendDocumentRequest().getDefendant();


        if (defendants.stream().anyMatch(d -> d.getViolatorId() == null)) {
            if (defendants.size() != 1) {
                throw new CourtValidationException("One of defendants has null violatorId");
            }
        }

        Function<ThirdCourtDefendantRequestDTO, Long> violatorIdSupplier = defendants.size() == 1
                ? defendant -> violatorService.findSingleByAdmCaseId(caseId).getId()
                : defendant -> violatorService.findByAdmCaseIdAndPersonId(caseId, defendant.getViolatorId());

        defendants.forEach(defendant -> {

            defendant.setViolatorId(violatorIdSupplier.apply(defendant));

            // заполнение потерпевшего
            defendant.getExactedDamage().forEach(damage -> {
                Long victimId = damage.getVictimId();
                if (victimId != null) {
                    damage.setVictimId(victimService.findByAdmCaseIdAndPersonId(caseId, victimId));
                }
            });
        });

        defendants.stream()
                .filter(defendant ->
                        defendant.getReturnReason() == null && requestDTO.getSendDocumentRequest().getStatus() == 17L &&
                                CourtFinalResultByInstanceAliases.getNameByValue(defendant.getFinalResult()).equals(CourtFinalResultByInstanceAliases.FR_I_CASE_RETURNING)
                )
                .forEach(defendant -> defendant.setReturnReason(99L));
    }
}
