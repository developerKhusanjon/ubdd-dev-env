package uz.ciasev.ubdd_service.service.signatre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.signature.DigitalSignatureCertificateResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureCertificateDTO;
import uz.ciasev.ubdd_service.service.user.UserAdminService;
import uz.ciasev.ubdd_service.service.user.UserDTOService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DigitalSignatureCertificateAdminServiceImpl implements DigitalSignatureCertificateAdminService {

    private final DigitalSignatureCertificateService certificateService;
    private final UserAdminService userAdminService;
    private final UserDTOService userDTOService;

    @Override
    @Transactional
    public DigitalSignatureCertificateResponseDTO create(User admin, Long userId) {
        User user = userAdminService.getByIdForAdmin(admin, userId);
        return map(certificateService.create(admin, user));
    }

    @Override
    @Transactional
    public DigitalSignatureCertificateResponseDTO exchange(User admin, Long userId) {
        User user = userAdminService.getByIdForAdmin(admin, userId);
        return map(certificateService.exchange(admin, user));
    }

    @Override
    @Transactional
    public void deactivateUserCurrentCertificate(User admin, Long userId, String reason) {
        User user = userAdminService.getByIdForAdmin(admin, userId);
        certificateService.deactivateUserCurrentCertificate(user, reason);
    }

    @Override
    @Transactional
    public void activate(User admin, Long userId, Long certificateId, String reason) {
        User user = userAdminService.getByIdForAdmin(admin, userId);
        DigitalSignatureCertificateDTO certificate = certificateService.getById(certificateId);
        if (!certificate.getUserId().equals(userId)) {
            throw new ValidationException(ErrorCode.USER_NOT_OWNER_OF_SIGNATURE_CERTIFICATE);
        }
        certificateService.activate(certificateId, reason);
    }

    @Override
    @Transactional
    public void sendUserCurrentCertificatePasswordBySms(User admin, Long userId) {
        User user = userAdminService.getByIdForAdmin(admin, userId);
        certificateService.sendUserCurrentCertificatePasswordToSms(user);
    }

    @Override
    public List<DigitalSignatureCertificateResponseDTO> getUserCertificates(User admin, Long userId) {
        User user = userAdminService.getByIdForAdmin(admin, userId);
        return certificateService.findByUser(user)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private DigitalSignatureCertificateResponseDTO map(DigitalSignatureCertificateDTO certificate) {
        InspectorResponseDTO createdUser = userDTOService.findInspectorById(certificate.getCreatedUserId());
        return new DigitalSignatureCertificateResponseDTO(certificate, createdUser);
    }
}
