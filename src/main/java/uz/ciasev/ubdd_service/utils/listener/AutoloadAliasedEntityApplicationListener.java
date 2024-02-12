package uz.ciasev.ubdd_service.utils.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.service.loading.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutoloadAliasedEntityApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    private final MessageLocalizationLoadService messageLocalizationService;

    private final AdmActionSyncService admActionLoadService;

    private final PermissionSyncService permissionSyncService;

    private final SingleThreadOperationTypeSyncService singleThreadOperationTypeSyncService;

    private final NotificationTypeSyncService notificationTypeSyncService;

    private final ViolationTypeTagSyncService violationTypeTagSyncService;

    @Value("${mvd-ciasev.autoload.permissions}")
    private boolean loadPermissions;

    @Value("${mvd-ciasev.autoload.actions}")
    private boolean loadActions;

    @Value("${mvd-ciasev.autoload.errors}")
    private boolean loadErrors;

    @Value("${mvd-ciasev.autoload.single-thread-operation-type}")
    private boolean loadSingleThreadOperationType;

    @Value("${mvd-ciasev.autoload.notification-type}")
    private boolean loadNotificationType;

    @Value("${mvd-ciasev.autoload.violation-type-tag}")
    private boolean loadViolationTypeTag;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        if (loadPermissions) {
            load("Permissions", permissionSyncService::sync);
        }

        if (loadActions) {
            load("Actions", admActionLoadService::sync);
        }

        if (loadErrors) {
            load("ErrorCodes", messageLocalizationService::load);
        }

        if (loadSingleThreadOperationType) {
            load("SingleThreadOperationType", singleThreadOperationTypeSyncService::sync);
        }

        if (loadNotificationType) {
            load("NotificationTypeAlias", notificationTypeSyncService::sync);
        }

        if (loadViolationTypeTag) {
            load("ArticleViolationTypeTagAlias", violationTypeTagSyncService::sync);
        }
    }

    private void load(String name, Loader loader) {
        log.info("Start load new {} into database", name);
        try {
            loader.call();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Loading error %s", name), e);
        }
        log.info("Finish load new {}", name);
    }

    public interface Loader {
        void call() throws Exception;
    }
}