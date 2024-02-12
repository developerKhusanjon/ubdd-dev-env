package uz.ciasev.ubdd_service.service.aop.signature;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.ciasev.ubdd_service.mvd_core.api.signature.service.SignatureApiService;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureEvent;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.signatre.DigitalSignatureCertificateService;
import uz.ciasev.ubdd_service.service.signatre.DigitalSignatureEventService;
import uz.ciasev.ubdd_service.service.user.SystemUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Aspect
@Order(1001)
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!ecpoff")
public class SignatureAspect {

    private final SignatureApiService signatureApiService;
    private final DigitalSignatureCertificateService digitalSignatureCertificateService;
    private final SystemUserService systemUserService;
    private final DigitalSignatureEventService digitalSignatureEventService;

    @Around("@annotation(DigitalSignatureCheck)")
    public Object signatureProxy(ProceedingJoinPoint joinPoint) throws Throwable {

        User currentUser = systemUserService.getCurrentUserOrThrow();

        if (currentUser == null || currentUser.getMustProvideDigitalSignature() == null || !currentUser.getMustProvideDigitalSignature()) {
            return joinPoint.proceed();
        }

        long currentTime = System.currentTimeMillis();

        SignatureEvent event = getAnnotatedEventType(joinPoint);

        List<String> signature = getHeaderSignature();

        DigitalSignatureCertificate userCertificate = digitalSignatureCertificateService.findSingleActiveByUser(currentUser);

        String extractedSignature = validateSignature(currentUser, userCertificate, signature.get(0), signature.get(1));

        currentTime -= System.currentTimeMillis();

        log.debug("DIGITAL SIGNATURE WORKAROUND OVERHEAD TIME: {}", currentTime);


        Object rsl = joinPoint.proceed();


        logSignatureEvent(currentUser, userCertificate, extractedSignature, signature.get(1), event, admEntityOfObject(rsl));

        return rsl;
    }

    private SignatureEvent getAnnotatedEventType(ProceedingJoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DigitalSignatureCheck ann = methodSignature.getMethod().getAnnotation(DigitalSignatureCheck.class);

        return ann.event();
    }

    private List<String> getHeaderSignature() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return List.of(
                Optional.ofNullable(request.getHeader("Signature")).orElseThrow(() -> new ValidationException(ErrorCode.DIGITAL_SIGNATURE_HEADER_NOT_FOUND)),
                Optional.ofNullable(request.getHeader("Signed-Data")).orElseThrow(() -> new ValidationException(ErrorCode.DIGITAL_SIGNATURE_HEADER_NOT_FOUND))
        );
    }

    private String validateSignature(User currentUser, DigitalSignatureCertificate userCertificate, String signature, String signedData) {

        // GET DATA DIGEST
        String digest = signatureApiService.makeDigestOfString(signedData);

        // EXTRACT SIGNATURE
        String extractedSignature = signatureApiService.extractSignature(signature);

        // VALIDATE SIGNATURE - extractedSignature
        boolean isVerified = signatureApiService.verifySignatureByCertificate(userCertificate.getSerial(), digest, extractedSignature);
        if (!isVerified) {
            throw new ValidationException(ErrorCode.USER_DIGITAL_SIGNATURE_VERIFICATION_FAILED);
        }

        return extractedSignature;
    }

    private void logSignatureEvent(User currentUser, DigitalSignatureCertificate userCertificate, String signature, String signedData, SignatureEvent event, @NonNull AdmEntity admEntity) {

        DigitalSignatureEvent eventEntity = new DigitalSignatureEvent();

        eventEntity.setUser(currentUser);
        eventEntity.setCertificate(userCertificate);
        eventEntity.setSignature(signature);
        eventEntity.setSignedData(signedData);
        eventEntity.setEventType(event);
        eventEntity.setEntityType(admEntity.getEntityNameAlias());
        eventEntity.setEntityId(admEntity.getId());

        digitalSignatureEventService.save(eventEntity);
    }

    private AdmEntity admEntityOfObject(final Object object) {

        if (object instanceof AdmEntity) {
            return (AdmEntity) object;
        }
        throw new ImplementationException(ErrorCode.CANNOT_LOG_SIGNATURE_EVENT_FOR_THE_ENTITY_THAT_IS_NOT_ADM_ENTITY);
    }
}
