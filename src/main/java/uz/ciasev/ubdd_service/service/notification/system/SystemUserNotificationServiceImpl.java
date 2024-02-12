package uz.ciasev.ubdd_service.service.notification.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SystemUserNotificationBroadcastRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.notification.SystemUserNotificationInfoResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.notification.SystemUserNotificationResponseDTO;
import uz.ciasev.ubdd_service.entity.notification.SystemUserNotification;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.notification.SystemNotificationRepository;
import uz.ciasev.ubdd_service.repository.user.UserRepository;
import uz.ciasev.ubdd_service.specifications.SystemUserNotificationSpecifications;
import uz.ciasev.ubdd_service.specifications.UserSpecifications;
import uz.ciasev.ubdd_service.utils.filters.BooleanFilter;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.StringFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class SystemUserNotificationServiceImpl implements SystemUserNotificationService {

    private final UserRepository userRepository;
    private final UserSpecifications userSpecifications;
    private final SystemUserNotificationSpecifications notificationSpecifications;
    private final SystemNotificationRepository systemNotificationRepository;
    private final FilterHelper<SystemUserNotification> filterHelper;

    public SystemUserNotificationServiceImpl(UserRepository userRepository, UserSpecifications userSpecifications, SystemUserNotificationSpecifications notificationSpecifications, SystemNotificationRepository systemNotificationRepository) {
        this.userRepository = userRepository;
        this.userSpecifications = userSpecifications;
        this.notificationSpecifications = notificationSpecifications;
        this.systemNotificationRepository = systemNotificationRepository;

        filterHelper = new FilterHelper<>(
                Pair.of("isRead", new BooleanFilter<>(this.notificationSpecifications::withIsRead)),
                Pair.of("eventType", new StringFilter<>(this.notificationSpecifications::withNotificationType))
        );
    }

    @Override
    public Page<SystemUserNotificationResponseDTO> findAllNotificationsByUser(User user, Map<String, String> filterParams, Pageable pageable) {
        return systemNotificationRepository
                .findAll(
                        notificationSpecifications.withUserId(user.getId())
                                .and(filterHelper.getParamsSpecification(filterParams)),
                        pageable
                ).map(SystemUserNotificationResponseDTO::new);
    }

    @Override
    public SystemUserNotificationInfoResponseDTO getNotificationsInfoByUser(User user) {
        Long unreadCount = systemNotificationRepository.getUnreadCountByUser(user.getId());
        return new SystemUserNotificationInfoResponseDTO(unreadCount);
    }

    @Override
    @Transactional
    public void markRead(User user, Collection<Long> notificationIds) {
        systemNotificationRepository.makeReadByUser(user.getId(), notificationIds);
    }

    @Override
    public void sendBroadcast(SystemUserNotificationBroadcastRequestDTO requestDTO) {
        List<Long> notifiedUsersId = getNotifiedUsers(requestDTO);


        if (requestDTO.getDecisionId() == null) {
            systemNotificationRepository.createForUsers(notifiedUsersId, requestDTO.getAdmCaseId(), requestDTO.getType().name(), requestDTO.getText());
        } else {
            systemNotificationRepository.createForUsers(notifiedUsersId, requestDTO.getAdmCaseId(), requestDTO.getDecisionId(), requestDTO.getType().name(), requestDTO.getText());
        }

//        List<SystemUserNotification> notifications = notifiedUsersId
//                .stream()
//                .map(userId -> SystemUserNotification.builder()
//                        .userId(userId)
//                        .admCaseId(requestDTO.getAdmCaseId())
//                        .decisionId(requestDTO.getDecisionId())
//                        .notificationTypeAlias(requestDTO.getType())
//                        .text(requestDTO.getText())
//                        .build())
//                .collect(Collectors.toList());
//
//        systemNotificationRepository.saveAll(notifications);

    }

    private List<Long> getNotifiedUsers(SystemUserNotificationBroadcastRequestDTO requestDTO) {
        return userRepository.findAllId(userSpecifications.withIsSystemNotificationSubscriber(true)
                .and(userSpecifications.withIsActive(true))
                .and(userSpecifications.withIsConsider(true))
                .and(userSpecifications.withOrganExactly(requestDTO.getOrgan()))
                .and(userSpecifications.withDepartmentExactly(requestDTO.getDepartment()))
                .and(userSpecifications.withRegionExactly(requestDTO.getRegion()))
                .and(userSpecifications.withDistrictExactly(requestDTO.getDistrict())));
    }
}
