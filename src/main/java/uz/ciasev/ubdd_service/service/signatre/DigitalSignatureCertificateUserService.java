package uz.ciasev.ubdd_service.service.signatre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.signature.MyDigitalSignatureCertificateCreateResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.signature.MyDigitalSignatureCertificateResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.signature.UserAlreadyHasDigitalSignatureError;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureCertificateDTO;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureKeyDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DigitalSignatureCertificateUserService {

    private final DigitalSignatureCertificateService certificateService;

    public MyDigitalSignatureCertificateCreateResponseDTO create(User user) {
        if (certificateService.existByUser(user)) {
            throw new UserAlreadyHasDigitalSignatureError();
        }

        DigitalSignatureCertificateDTO signature = certificateService.create(user, user);
        boolean isSmsSend = sendPasswordSms(user);

        return new MyDigitalSignatureCertificateCreateResponseDTO(signature, isSmsSend);
    }

    public MyDigitalSignatureCertificateCreateResponseDTO exchange(User user) {
        DigitalSignatureCertificateDTO signature =  certificateService.exchange(user, user);
        boolean isSmsSend = sendPasswordSms(user);

        return new MyDigitalSignatureCertificateCreateResponseDTO(signature, isSmsSend);
    }

    public DigitalSignatureKeyDTO getUserCurrentCertificatePrivateKey(User user) {
        return certificateService.getUserCurrentCertificatePrivateKey(user);
    }

    public List<MyDigitalSignatureCertificateResponseDTO> getUserCertificates(User user) {
        return certificateService.findByUser(user).stream().map(MyDigitalSignatureCertificateResponseDTO::new).collect(Collectors.toList());
    }

    private boolean sendPasswordSms(User user) {
        try {
            certificateService.sendUserCurrentCertificatePasswordToSms(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
