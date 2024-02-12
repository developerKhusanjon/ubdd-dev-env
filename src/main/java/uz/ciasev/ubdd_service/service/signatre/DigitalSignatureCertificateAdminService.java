package uz.ciasev.ubdd_service.service.signatre;

import uz.ciasev.ubdd_service.dto.internal.response.signature.DigitalSignatureCertificateResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface DigitalSignatureCertificateAdminService {

    DigitalSignatureCertificateResponseDTO create(User admin, Long userId);

    DigitalSignatureCertificateResponseDTO exchange(User admin, Long userId);

    void deactivateUserCurrentCertificate(User admin, Long userId, String reason);

    void activate(User admin, Long userId, Long certificateId, String reason);

    void sendUserCurrentCertificatePasswordBySms(User admin, Long userId);

    List<DigitalSignatureCertificateResponseDTO> getUserCertificates(User admin, Long userId);
}
