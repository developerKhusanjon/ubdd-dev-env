package uz.ciasev.ubdd_service.service.autocon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.service.AutoconApiDTOService;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.service.AutoconApiService;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconCloseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconOpenDTO;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;

@Service
@RequiredArgsConstructor
public class AutoconSendingHandleServiceImpl implements AutoconSendingHandleService {

    private final AutoconApiService apiService;
    private final AutoconApiDTOService dtoService;
    private final AutoconSendingService autoconSendingService;

    @Override
    public void handle(AutoconSending sending) {
        if (AutoconSendingStatusAlias.AWAIT_OPEN.equals(sending.getStatus())) {
            open(sending);
        } else if (AutoconSendingStatusAlias.AWAIT_CLOSE.equals(sending.getStatus())) {
            close(sending);
        }
    }

    private void open(AutoconSending sending) {
        AutoconOpenDTO requestBody = dtoService.buildOpenDTO(sending);
        apiService.addDebtor(requestBody);
        autoconSendingService.opened(sending);
    }

    private void close(AutoconSending sending) {
        AutoconCloseDTO requestBody = dtoService.buildCloseDTO(sending);
        apiService.deleteDebtor(requestBody);
        autoconSendingService.closed(sending);
    }
}
