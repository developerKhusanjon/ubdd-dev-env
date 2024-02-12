package uz.ciasev.ubdd_service.service.pdf;

import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;

public interface PdfModelPhotoService {

    String getProtocolOrganPhoto(Protocol protocol);

    String getViolatorProto(Violator violatorDetail);

    String getResolutionOrganLogo(Resolution resolution);

    List<String> getScenePhotos(Long admCaseId);

    String getUserOrganLogo(User user);
}
