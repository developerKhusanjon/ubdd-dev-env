package uz.ciasev.ubdd_service.controller_ubdd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uz.ciasev.ubdd_service.config.security.CurrentUser;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ProtocolDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.main.protocol.ProtocolCreateService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolDTOService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${mvd-ciasev.url-v0}/adm-cases/", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UbddProtocolController {

    private final ProtocolCreateService protocolCreateService;
    private final ProtocolDTOService dtoService;

    @PostMapping("protocol")
    @ResponseStatus(HttpStatus.CREATED)
    public ProtocolDetailResponseDTO createProtocol(@CurrentUser User user,
                                                    @RequestBody @Valid ProtocolRequestDTO protocol) {

        return dtoService.buildDetailForCreateProtocol(user, () -> protocolCreateService.createElectronProtocol(user, protocol));

    }

}