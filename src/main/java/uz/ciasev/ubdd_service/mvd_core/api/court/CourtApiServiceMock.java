package uz.ciasev.ubdd_service.mvd_core.api.court;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResultDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtPaymentDTO;
import uz.ciasev.ubdd_service.exception.court.CourtResult;
import uz.ciasev.ubdd_service.service.court.CourtLogService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Random;

import static uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod.COURT_FIRST;

@Slf4j
@Service
@Primary
@Profile({"local", "test"})
@RequiredArgsConstructor
public class CourtApiServiceMock implements CourtApiService {

    private final CourtLogService courtLogService;
    private Random rnd = new Random();

    @Override
    public CourtSendResult sendAdmCase(CourtRequestDTO<FirstCourtAdmCaseRequestDTO> requestBody) {

        CourtSendResult res = postForCourtResult(
                "/adm/api/mvd-case/case/create",
                requestBody
        );

        courtLogService.save(requestBody.getSendDocumentRequest().getCaseId(), COURT_FIRST, res);

        return res;
    }

    @Override
    public CourtSendResult sendInvoiceWithPayment(CourtRequestDTO<FourthCourtPaymentDTO> requestBody) {
        return postForCourtResult(
                "/adm/api/mvd-case/receipt/create",
                requestBody
        );
    }

    @Override
    public byte[] getByteByUri(String uri) {
        return new byte[0];
    }

    private CourtSendResult postForCourtResult(String uri, CourtRequestDTO requestBody) {

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String mockMod = Optional.ofNullable(request.getParameter("mockMod")).orElse("VALIDATION");

        switch (mockMod) {
            case "OK":
                return new CourtSendResult(new CourtResponseDTO(
                        new CourtResultDTO(CourtResult.SUCCESSFULLY, "MOCK OK MESSAGE"),
                        Integer.valueOf(rnd.nextInt()).longValue()
                ));
            case "VALIDATION":
                return new CourtSendResult(new CourtResponseDTO(
                        new CourtResultDTO(CourtResult.VALIDATION_ERROR, "MOCK VALIDATION MESSAGE"),
                        Integer.valueOf(rnd.nextInt()).longValue()
                ));

            default:
                throw new CourtApiServerException(new Exception("MOCK EXCEPTION MESSAGE"));
        }
    }
}
