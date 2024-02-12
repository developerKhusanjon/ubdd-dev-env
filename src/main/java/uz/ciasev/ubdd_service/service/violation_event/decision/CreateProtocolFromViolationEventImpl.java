package uz.ciasev.ubdd_service.service.violation_event.decision;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindInternalDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.service.main.protocol.ProtocolBaseCreateService;
import uz.ciasev.ubdd_service.service.ubdd_data.UbddTexPassDataService;
import uz.ciasev.ubdd_service.service.violation_event.build.ViolationEventFabulaBuildingService;
import uz.ciasev.ubdd_service.service.violation_event.build.ViolationEventProtocolBuildingService;
import uz.ciasev.ubdd_service.service.violation_event.build.ViolationEventViolatorBuildingService;

@Service
@RequiredArgsConstructor
class CreateProtocolFromViolationEventImpl implements CreateProtocolFromViolationEvent {
    private final ViolationEventProtocolBuildingService protocolBuildingService;
    private final ViolationEventViolatorBuildingService violatorBuildingService;
    private final UbddTexPassDataService texPassStorageService;
    private final ProtocolBaseCreateService protocolBaseCreateService;
    private final ViolationEventFabulaBuildingService fabulaBuilder;


    @Override
    @Transactional
    public Pair<Protocol, UbddTexPassData> create(User user, ViolationEventApiDTO event, AdmCase admCase, UbddTexPassDTOI texPassData) {
        if (texPassData.getVehicleOwnerType().getIsJuridic()) {
            throw new NotImplementedException("Make decision on juridic not implemented yet");
        }

        ViolatorCreateRequestDTO violatorRequest = violatorBuildingService.build(texPassData);
        ProtocolRequestDTO protocolRequest = protocolBuildingService.build(event);
        protocolRequest.setViolator(violatorRequest);

        UbddTexPassData savedTexPass = texPassStorageService.save(user, texPassData);
        protocolRequest.setUbddDataBind(UbddDataToProtocolBindInternalDTO.ofTexPass(savedTexPass));

        Protocol protocol = protocolBaseCreateService.createProtocol(
                user,
                protocolRequest,
                () -> admCase,
                personValidator -> {
                },
                violatorDetail -> fabulaBuilder.build(event, violatorRequest, violatorDetail.getViolator().getPerson(), texPassData)
        );

        return Pair.of(protocol, savedTexPass);
    }

}
