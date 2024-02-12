package uz.ciasev.ubdd_service.service.signatre;


import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureCertificateDTO;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureKeyDTO;

import java.util.List;

public interface DigitalSignatureCertificateService {

    DigitalSignatureCertificateDTO getById(Long id);

    List<DigitalSignatureCertificateDTO> findByUser(User user);

    boolean existByUser(User user);

    DigitalSignatureCertificateDTO create(User createdUser, User forUser);

    DigitalSignatureCertificateDTO exchange(User createdUser, User forUser);

    void deactivateUserCurrentCertificate(User user, String reason);

    void activate(Long certificateId, String reason);

//    DigitalSignaturePasswordDTO getUserCurrentCertificatePassword(User user);

    @Transactional
    void sendUserCurrentCertificatePasswordToSms(User user);

    DigitalSignatureKeyDTO getUserCurrentCertificatePrivateKey(User user);

    DigitalSignatureCertificate findActiveByUser(User user);

    DigitalSignatureCertificate findSingleActiveByUser(User user);
}
