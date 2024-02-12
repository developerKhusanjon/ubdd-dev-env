package uz.ciasev.ubdd_service.mvd_core.api.court.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtWebhookService;
import uz.ciasev.ubdd_service.config.security.CurrentUser;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth.FiveCourtMibDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.material.CourtMaterialRegistrationRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestExternalDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh.CourtDecisionFileRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.court.CourtResult;
import uz.ciasev.ubdd_service.exception.court.ExternalException;

import javax.annotation.PreDestroy;
import javax.validation.Valid;
import java.util.EnumMap;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${external-systems.url-v0}/court/adm-cases/")
@Profile("emi-api")
public class CourtExternalApiController {

    private final CourtWebhookService courtWebhookService;

    private final EnumMap<CourtMethod, ExecutorService> executorMap = new EnumMap<>(CourtMethod.class);

    {
        Stream.of(
                CourtMethod.COURT_SECOND,
                CourtMethod.COURT_THIRD,
                CourtMethod.COURT_FIFTH,
                CourtMethod.COURT_SEVEN,
                CourtMethod.COURT_NINE,
                CourtMethod.COURT_SECOND_MATERIAL
        ).forEach(method -> executorMap.put(method, Executors.newFixedThreadPool(1)));
    }

    @PreDestroy
    private void destroy() {
        executorMap.values().forEach(ExecutorService::shutdown);
    }

    /**
     * Second method
     **/
    @PostMapping("/registration-status")
    public CourtResponseDTO admCaseRegistrationStatus(@CurrentUser User user,
                                                      @RequestBody @Valid CourtRequestDTO<CourtRegistrationStatusRequestDTO> requestDTO) {
        return executeInPool(CourtMethod.COURT_SECOND, courtWebhookService::acceptSecondMethod, requestDTO);
    }

    /**
     * Second material method
     **/
    @PostMapping("/material/register")
    public CourtResponseDTO materialRegistration(@CurrentUser User user,
                                                 @RequestBody @Valid CourtRequestDTO<CourtMaterialRegistrationRequestDTO> requestDTO) {
        return executeInPool(CourtMethod.COURT_SECOND_MATERIAL, courtWebhookService::acceptSecondMaterialMethod, requestDTO);
    }

    /**
     * Third method
     **/
    @PostMapping("/resolution")
    public CourtResponseDTO executeResolution(@CurrentUser User user,
                                              @RequestBody @Valid CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO) {
        return executeInPool(CourtMethod.COURT_THIRD, courtWebhookService::acceptThirdMethod, requestDTO);
    }

    /**
     * Fifth method
     **/
    @PostMapping("/compulsory-execution")
    public CourtResponseDTO executeCompulsory(@CurrentUser User user,
                                              @RequestBody @Valid CourtRequestDTO<FiveCourtMibDTO> requestDTO) {
        return executeInPool(CourtMethod.COURT_FIFTH, courtWebhookService::acceptFiveMethod, requestDTO);
    }

    /**
     * Seven method
     **/
    @PostMapping("/decision-file")
    public CourtResponseDTO admCaseSaveDecisionFile(@CurrentUser User user,
                                                    @RequestBody @Valid CourtRequestDTO<CourtDecisionFileRequestDTO> requestDTO) {
        return executeInPool(CourtMethod.COURT_SEVEN, courtWebhookService::acceptSevenMethod, requestDTO);
    }

    /**
     * Eight method
     **/
    @GetMapping("/resolution-data")
    public CourtRequestDTO findResolutionBySeriesAndNumber(@CurrentUser User user,
                                                           @RequestParam(name = "series") String series,
                                                           @RequestParam(name = "number") String number) {
        return courtWebhookService.acceptEightMethod(series, number);
    }

    /**
     * Nine method
     **/
    @PostMapping("/victims")
    public CourtResponseDTO createVictims(@CurrentUser User user,
                                          @RequestBody @Valid CourtRequestDTO<CourtVictimRequestExternalDTO> dto) {
        return executeInPool(CourtMethod.COURT_NINE, courtWebhookService::acceptNineMethod, dto);
    }

    @GetMapping("/health-checkAndRemember")
    public String healthCheck() {
        return "I'm alive";
    }

    private <T> CourtResponseDTO executeInPool(CourtMethod method, Function<CourtRequestDTO<T>, CourtResponseDTO> handler, CourtRequestDTO<T> request) {
        ExecutorService pool = executorMap.get(method);
        Callable<CourtResponseDTO> callable = () -> handler.apply(request);

        Future<CourtResponseDTO> feature = pool.submit(callable);
        try {
            return feature.get();
        } catch (InterruptedException e) {
            throw new ExternalException(CourtResult.SINGLE_TREAD_INTERRUPTED_ERROR, e.getMessage());
        } catch (ExecutionException e) {
            Throwable causeError = e.getCause();
            if (causeError instanceof RuntimeException) {
                throw (RuntimeException) causeError;
            }

            throw new ExternalException(CourtResult.SINGLE_TREAD_EXECUTION_ERROR, e.getMessage());
        }
    }
}