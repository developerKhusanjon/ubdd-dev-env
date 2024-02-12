package uz.ciasev.ubdd_service.service.violation_event.build;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ViolationEventProtocolBuildingServiceImpl implements ViolationEventProtocolBuildingService {

    @Override
    public ProtocolRequestDTO build(ViolationEventApiDTO violationEvent) {
        ProtocolRequestDTO protocol = new ProtocolRequestDTO();

        protocol.setIsJuridic(false);
        protocol.setJuridic(null);
        protocol.setAdditionArticles(null);
        protocol.setArticlePart(violationEvent.getArticlePart());
        protocol.setArticleViolationType(violationEvent.getViolationType());
        protocol.setRepeatabilityProtocolsId(null);
        protocol.setFabulaAdditional(null);

//        private LocalDateTime registrationTime;
        protocol.setRegion(violationEvent.getRegion());
        protocol.setDistrict(violationEvent.getDistrict());
        protocol.setAddress(violationEvent.getAddress());
        protocol.setViolationTime(violationEvent.getViolationTime());
        protocol.setRegistrationTime(LocalDateTime.now());
        protocol.setIsFamiliarize(true);
        protocol.setIsAgree(true);
        protocol.setExplanatoryText("");
        protocol.setLatitude(violationEvent.getLatitude());
        protocol.setLongitude(violationEvent.getLongitude());
        protocol.setVehicleNumber(violationEvent.getVehicleNumber());
//        private UbddDataToProtocolBindInternalDTO ubddDataBind;
//        возможно стоит добавить признак фото-видео протакола

        return protocol;
    }
}
