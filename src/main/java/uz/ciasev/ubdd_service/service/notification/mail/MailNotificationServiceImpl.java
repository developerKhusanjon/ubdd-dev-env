package uz.ciasev.ubdd_service.service.notification.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.notification.mail.*;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ForbiddenException;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.notification.mail.MailNotificationRepository;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.notification.sms.MailNotificationFileRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.generator.MailNumberGeneratorService;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.service.settings.OrganSettingsService;
import uz.ciasev.ubdd_service.specifications.MailNotificationSpecifications;
import uz.ciasev.ubdd_service.utils.FormatUtils;
import uz.ciasev.ubdd_service.utils.filters.*;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class MailNotificationServiceImpl implements MailNotificationService {

    private final MailNumberGeneratorService numberGeneratorService;
    private final AddressService addressService;
    private final MailTransGeographyService mailTransGeographyService;
    private final MailNotificationRepository mailNotificationRepository;
    private final MailNotificationFileRepository mailNotificationFileRepository;
    private final MailNotificationSpecifications specifications;
    private final OrganSettingsService organSettingsService;
    private final FilterHelper<MailNotification> filterHelper;

    @Autowired
    public MailNotificationServiceImpl(MailNumberGeneratorService numberGeneratorService,
                                       AddressService addressService,
                                       MailTransGeographyService mailTransGeographyService,
                                       MailNotificationRepository mailNotificationRepository,
                                       MailNotificationFileRepository mailNotificationFileRepository, MailNotificationSpecifications mailNotificationSpecifications,
                                       OrganSettingsService organSettingsService) {

        this.numberGeneratorService = numberGeneratorService;
        this.addressService = addressService;
        this.mailTransGeographyService = mailTransGeographyService;
        this.mailNotificationRepository = mailNotificationRepository;
        this.mailNotificationFileRepository = mailNotificationFileRepository;
        this.specifications = mailNotificationSpecifications;
        this.organSettingsService = organSettingsService;

        filterHelper = new FilterHelper<>(
                Pair.of("violatorFirstName", new StringFilter<>(mailNotificationSpecifications::withViolatorFirstName)),
                Pair.of("violatorSecondName", new StringFilter<>(mailNotificationSpecifications::withViolatorSecondName)),
                Pair.of("violatorLastName", new StringFilter<>(mailNotificationSpecifications::withViolatorLastName)),
                Pair.of("violatorBirthDateAfter", new DateFilter<>(mailNotificationSpecifications::withViolatorBirthAfter)),
                Pair.of("violatorBirthDateBefore", new DateFilter<>(mailNotificationSpecifications::withViolatorBirthBefore)),
                Pair.of("violatorDocumentNumber", new StringFilter<>(mailNotificationSpecifications::withViolatorDocumentNumber)),
                Pair.of("violatorDocumentSeries", new StringFilter<>(mailNotificationSpecifications::withViolatorDocumentSeries)),
                Pair.of("type", new StringFilter<>(mailNotificationSpecifications::withNotificationType)),
                Pair.of("typeId", new LongFilter<>(mailNotificationSpecifications::withNotificationTypeId)),
                Pair.of("deliveryStatusId", new LongFilter<>(mailNotificationSpecifications::withDeliveryStatusId)),
                Pair.of("messageNumber", new StringFilter<>(mailNotificationSpecifications::withMessageNumber)),
                Pair.of("sendDateAfter", new DateFilter<>(mailNotificationSpecifications::withSendDateAfter)),
                Pair.of("sendDateBefore", new DateFilter<>(mailNotificationSpecifications::withSendDateBefore)),
                Pair.of("performDateAfter", new DateFilter<>(mailNotificationSpecifications::withPerformDateAfter)),
                Pair.of("performDateBefore", new DateFilter<>(mailNotificationSpecifications::withPerformDateBefore)),
                Pair.of("decisionNumber", new StringFilter<>(mailNotificationSpecifications::withDecisionNumber)),
                Pair.of("resolutionTimeFrom", new DateFilter<>(mailNotificationSpecifications::withResolutionTimeAfter)),
                Pair.of("resolutionTimeTo", new DateFilter<>(mailNotificationSpecifications::withResolutionTimeBefore)),
                Pair.of("decisionArticlePartId", new LongFilter<>(mailNotificationSpecifications::withDecisionArticlePartIdIn)),
                Pair.of("decisionOrganId", new LongFilter<>(mailNotificationSpecifications::withAdmCaseOrganId)),
                Pair.of("decisionDepartmentId", new LongFilter<>(mailNotificationSpecifications::withAdmCaseDepartmentId)),
                Pair.of("decisionRegionId", new LongFilter<>(mailNotificationSpecifications::withAdmCaseRegionId)),
                Pair.of("decisionDistrictId", new LongFilter<>(mailNotificationSpecifications::withAdmCaseDistrictId)),
                Pair.of("statusIdIn", new SetFilter<>(mailNotificationSpecifications::withStatusIdIn))
        );
    }

    @Override
    public MailNotification sendMail(@Nullable User user, Decision decision, NotificationTypeAlias notificationTypeAlias, MileContentBuilder contentBuilder) {
        checkOrganAccess(decision);
        checkDuplicateSend(decision, notificationTypeAlias);
        OrganInfo organInfo = organSettingsService.getOrganInfo(decision.getResolution());

        Violator violator = decision.getViolator();
        Person person = violator.getPerson();

        Address postAddress = getPostAddress(violator);
        MailTransGeography geography = mailTransGeographyService
                .findByRegionAndDistrict(postAddress.getRegionId(), postAddress.getDistrictId());

        MailNotificationRequest mail = new MailNotificationRequest();
        mail.setUser(user);
        mail.setViolator(violator);
        mail.setDecision(decision);
        mail.setNotificationType(notificationTypeAlias);
        mail.setGeography(geography);
//        mail.setAreaId(geography.getExternalDistrictId());
//        mail.setRegionId(geography.getExternalRegionId());
        mail.setFio(person.getFIOLat());
        mail.setAddress(FormatUtils.addressToText(postAddress));
        mail.setOrganInfo(organInfo);
        mail.setOrgan(decision.getResolution().getOrgan());

        String messageNumber = numberGeneratorService.generateNumber();
        byte[] mailBytes = contentBuilder.getPdfContent(decision, organInfo, messageNumber);

        mail.setMessageNumber(messageNumber);
        mail.setBase64Content(mailBytes);

        return create(mail);
    }

    @Override
    public Page<MailNotificationListProjection> findByFilters(User user, Map<String, String> filtersValue, Pageable pageable) {
        Specification<MailNotification> specification = filterHelper.getParamsSpecification(filtersValue);
        Page<Long> idPage = mailNotificationRepository.findAllId(specification, pageable);

        List<MailNotificationListProjection> result = mailNotificationRepository.findListProjectionById(idPage.getContent(), pageable.getSort());

        return new PageImpl<>(result, pageable, idPage.getTotalElements());
    }

    @Override
    public List<MailNotification> findByDecision(Long decisionId) {
        return mailNotificationRepository.findAll(
                specifications.withEntityAlias(EntityNameAlias.DECISION)
                        .and(specifications.withEntityId(decisionId))
        );
    }

    @Override
    public List<MailNotification> findByEntityAndEvent(AdmEntity entity, NotificationTypeAlias notificationTypeAlias) {
        return mailNotificationRepository.findAll(
//                specifications.withDeliveryStatusCode(MailDeliveryCode.SuccessDelivered)
                specifications.withEntityAlias(entity.getEntityNameAlias())
                        .and(specifications.withType(notificationTypeAlias))
                        .and(specifications.withEntityId(entity.getId())),
                Sort.by(MailNotification_.sendTime.getName()).descending()
        );
    }

    @Override
    public MailNotification getById(Long id) {
        return mailNotificationRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(MailNotification.class, id));
    }

    private MailNotificationFile getFileByMailNotificationId(Long id) {
        return mailNotificationFileRepository.findByMailNotificationId(id)
                .orElseThrow(() -> new EntityByParamsNotFound(MailNotificationFile.class, "mailNotificationId", id));
    }

    @Override
    @Transactional
    public byte[] getContent(Long id) {
        return getFileByMailNotificationId(id).getContent();
    }

    @Override
    public List<MailNotification> getByIds(List<Long> ids) {
        return mailNotificationRepository.findAll(specifications.withIdIn(ids));
    }

    private void checkDuplicateSend(Decision decision, NotificationTypeAlias eventType) {
        if (hasNotification(decision, eventType)) {
            throw new ValidationException(ErrorCode.DECISION_MAIL_ALREADY_SEND);
        }
    }

    public boolean hasNotification(Decision decision, NotificationTypeAlias notificationTypeAlias) {
        return mailNotificationRepository.existsByDecisionAndNotificationTypeAlias(decision, notificationTypeAlias);
    }

    private Address getPostAddress(Violator violator) {
        Address postAddress = addressService.findById(violator.getPostAddressId());
        if (postAddress.getDistrictId() == null) {
            throw new ValidationException(ErrorCode.POST_ADDRESS_INVALID);
        }

        return postAddress;
    }

    private void checkOrganAccess(Decision decision) {
        if (!decision.getResolution().getOrgan().getMailNotification()) {
            throw new ForbiddenException(ErrorCode.SERVICE_NOT_ALLOWED_FOR_ORGAN, "Send mail not allowed for resolution organ");
        }
    }

    public interface MileContentBuilder {
        byte[] getPdfContent(Decision decision, OrganInfo organInfo, String mailNumber);
    }

    private MailNotification create(MailNotificationRequest request) {
        MailNotification mailNotification = mailNotificationRepository.save(new MailNotification(request));
        mailNotificationFileRepository.save(new MailNotificationFile(mailNotification, request.getBase64Content()));
        return mailNotification;
    }
}
