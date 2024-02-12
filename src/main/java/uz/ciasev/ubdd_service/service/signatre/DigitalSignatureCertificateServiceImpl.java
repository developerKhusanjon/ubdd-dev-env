package uz.ciasev.ubdd_service.service.signatre;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateCreateRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateCreateResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateDetailResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.signature.service.SignatureApiService;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SmsRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.history.DigitalSignatureRegistrationAction;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.signature.*;
import uz.ciasev.ubdd_service.repository.signature.DigitalSignatureCertificateRepository;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationDTOService;
import uz.ciasev.ubdd_service.service.notification.sms.SmsNotificationService;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureCertificateDTO;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureKeyDTO;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignaturePasswordDTO;
import uz.ciasev.ubdd_service.service.user.UserService;
import uz.ciasev.ubdd_service.utils.EncryptUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DigitalSignatureCertificateServiceImpl implements DigitalSignatureCertificateService {

    private final int PRE_REFRESH_DAYS = 30;
    private final DigitalSignatureCertificateRepository repository;
    private final HistoryService historyService;
    private final SignatureApiService signatureApiService;
    private final UserService userService;
    private final EncryptUtils encryptUtils;
    private final SmsNotificationService smsNotificationService;
    private final SmsNotificationDTOService smsNotificationDTOService;


    @Override
    public DigitalSignatureCertificateDTO getById(Long id) {
        return map(findById(id));
    }

    @Override
    public List<DigitalSignatureCertificateDTO> findByUser(User user) {
        return repository.findByUser(user)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existByUser(User user) {
        return repository.existsByUser(user);
    }

    @Override
    @Transactional
    public DigitalSignatureCertificateDTO create(User createdUser, User forUser) {
        checkUserHasNoActiveCertificate(forUser.getId());
        checkUserHasNoAliveCertificate(forUser.getId());

        DigitalSignatureCertificate certificate = generateAndSaveCertificate(createdUser, forUser);
        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.CREATE, certificate, null);

        return map(certificate);
    }

    @Override
    @Transactional
    public DigitalSignatureCertificateDTO exchange(User createdUser, User forUser) {
        DigitalSignatureCertificate certificate = findActiveByUser(forUser);

        if (validateVerifyPeriodComeToEnd(certificate)) {
            throw new DigitalSignaturePeriodValid();
        }

        certificate.editActivity(false, "Exchange expired certificate");
        repository.save(certificate);
        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.EXCHANGE_EXPIRED, certificate, null);


        DigitalSignatureCertificate newCertificate = generateAndSaveCertificate(createdUser, forUser);
        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.CREATE, newCertificate, Map.of("exchangedForId", certificate.getId().toString()));

        return map(newCertificate);
    }

    @Override
    @Transactional
    public void deactivateUserCurrentCertificate(User user, String reason) {
        DigitalSignatureCertificate certificate = findActiveByUser(user);

        certificate.editActivity(false, reason);
        repository.save(certificate);
        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.DEACTIVATE, certificate, Map.of("reason", reason));

        signatureApiService.pauseCertificate(certificate.getSerial(), reason);
    }

    @Override
    @Transactional
    public void activate(Long certificateId, String reason) {
        DigitalSignatureCertificate certificate = findById(certificateId);

        checkUserHasNoActiveCertificate(certificate.getUserId());
        if (!validateVerifyPeriod(certificate)) {
            throw new DigitalSignaturePeriodExpired();
        }

        certificate.editActivity(true, reason);
        repository.save(certificate);
        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.ACTIVATE, certificate, Map.of("reason", reason));

        signatureApiService.resumeCertificate(certificate.getSerial(), reason);
    }

    @Override
    @Transactional
    public void sendUserCurrentCertificatePasswordToSms(User user) {
        DigitalSignatureCertificate certificate = findActiveByUser(user);
        if (!validateVerifyPeriod(certificate)) {
            throw new DigitalSignaturePeriodExpired();
        }

        DigitalSignaturePasswordDTO passwordDTO = new DigitalSignaturePasswordDTO(
                certificate,
                encryptUtils.decrypt(certificate.getEncryptedPassword())
        );

        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.REQUEST_PASSWORD, certificate, Map.of("userPhone", user.getMobile()));

        SmsRequestDTO sms = smsNotificationDTOService.makeUserDigitalSignatureCertificatePasswordDTO(
                user,
                passwordDTO,
                userService.getUserRelateOrgan(user)
        );
        smsNotificationService.sendSecretSms(sms);
    }

    @Override
    @Transactional
    public DigitalSignatureKeyDTO getUserCurrentCertificatePrivateKey(User user) {
        DigitalSignatureCertificate certificate = findActiveByUser(user);
        if (!validateVerifyPeriod(certificate)) {
            throw new DigitalSignaturePeriodExpired();
        }

        CertificateDetailResponseDTO apiCertificate = signatureApiService.getCertificate(certificate.getSerial());
        DigitalSignatureKeyDTO response = new DigitalSignatureKeyDTO(
                certificate,
                apiCertificate.getPrivateKey()
        );

        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.REQUEST_PRIVATE_KEY, certificate, Map.of());

        return response;
    }

    @Override
    public DigitalSignatureCertificate findSingleActiveByUser(User user) {
        if (repository.existsMoreThenOneByUserId(user.getId())) {
            throw new ValidationException(ErrorCode.USER_HAS_MORE_THEN_ONE_ACTIVE_CERTIFICATE);
        }
        return findActiveByUser(user);
    }

    @Override
    public DigitalSignatureCertificate findActiveByUser(User user) {
        return repository.findActiveByUser(user)
                .orElseThrow(UserHasNoActiveDigitalSignatureError::new);
    }

    private DigitalSignatureCertificate findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(DigitalSignatureCertificate.class, id));
    }

    private boolean validateVerifyPeriod(DigitalSignatureCertificate certificate) {
        return certificate.getExpiresOn().isAfter(LocalDateTime.now());
    }

    private boolean validateVerifyPeriodComeToEnd(DigitalSignatureCertificate certificate) {
        return certificate.getExpiresOn().isAfter(LocalDateTime.now().plusDays(PRE_REFRESH_DAYS));
    }

    private boolean validateUserHasActiveCertificate(Long userId) {
        return repository.existsActiveByUserId(userId);
    }

    private boolean validateUserHasAliveCertificate(Long userId) {
        return repository.existsAliveByUserId(
                userId,
                LocalDateTime.now().plusDays(PRE_REFRESH_DAYS)
        );
    }

    private void checkUserHasNoActiveCertificate(Long userId) {
        if (validateUserHasActiveCertificate(userId)) {
            throw new UserAlreadyHasActiveDigitalSignatureError();
        }
    }

    private void checkUserHasNoAliveCertificate(Long userId) {
        if (validateUserHasAliveCertificate(userId)) {
            throw new UserAlreadyHasAliveDigitalSignatureError();
        }
    }

    private DigitalSignatureCertificate generateAndSaveCertificate(User createdUser, User forUser) {
        Organ userOrgan = userService.getUserRelateOrgan(forUser);

        CertificateCreateRequestDTO request = CertificateCreateRequestDTO.builder()
                .firstName(forUser.getFirstNameLat())
                .secondName(forUser.getSecondNameLat())
                .lastName(forUser.getLastNameLat())
//                .organ("dev")
                .organ(userOrgan.getDefaultName())
                .department(Optional.ofNullable(forUser.getDepartment()).map(Department::getDefaultName).orElse(""))
                .region(Optional.ofNullable(forUser.getRegion()).map(Region::getDefaultName).orElse(""))
                .district(Optional.ofNullable(forUser.getDistrict()).map(District::getDefaultName).orElse(""))
                .position(forUser.getPosition().getDefaultName())
                .rank(forUser.getRank().getDefaultName())
                .build();

        CertificateCreateResponseDTO response = signatureApiService.createCertificate(request);

        DigitalSignatureCertificate certificate = DigitalSignatureCertificate.builder()
                .user(forUser)
                .createdUser(createdUser)
                .serial(response.getSerial())
                .encryptedPassword(encryptUtils.encrypt(response.getPassword()))
                .issuedOn(response.getIssuedOn())
                .expiresOn(response.getExpiresOn())
                .file(response.getCertificate())
                .build();

        return repository.save(certificate);
    }

    //    @Override
//    @Transactional
    private DigitalSignaturePasswordDTO getUserCurrentCertificatePassword(User user) {
        DigitalSignatureCertificate certificate = findActiveByUser(user);
        DigitalSignaturePasswordDTO response = new DigitalSignaturePasswordDTO(
                certificate,
                encryptUtils.decrypt(certificate.getEncryptedPassword())
        );

        historyService.registerDigitalSignatureEvent(DigitalSignatureRegistrationAction.REQUEST_PASSWORD, certificate, Map.of("userPhone", user.getMobile()));

        return response;
    }

    private DigitalSignatureCertificateDTO map(DigitalSignatureCertificate certificate) {
        return new DigitalSignatureCertificateDTO(certificate, !validateVerifyPeriodComeToEnd(certificate));
    }


}
