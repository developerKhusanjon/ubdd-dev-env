//package uz.ciasev.ubdd_service.service.aop.replica;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.core.config.Order;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
//import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
//import uz.ciasev.ubdd_service.service.replica.ReplicaWebhookCreateService;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@Aspect
//@Order(1002)
//@Component
//@RequiredArgsConstructor
//public class ReplicaWebhookEventAspect {
//
//    private final ReplicaWebhookCreateService replicaWebhookCreateService;
//
//    @Around("@annotation(ReplicaFireEvent)")
//    public Object acceptAndApply(ProceedingJoinPoint joinPoint, ReplicaFireEvent replicaFireEvent) throws Throwable {
//        Object result = joinPoint.proceed();
//        log.debug("Replica webhook event start: {}", LocalDateTime.now());
//
//        switch (replicaFireEvent.type()) {
//            case PROTOCOL_CREATE:
//            case DECISIONS_MADE:
//            case ORGAN_RESOLUTION_CREATE:
//                Arrays.stream(joinPoint.getArgs()).filter(AdmCase.class::isInstance).findFirst().ifPresent(obj -> replicaWebhookCreateService.createWebhooks((AdmCase) obj));
//                break;
//            case DECISIONS_CANCEL: {
//                Arrays.stream(joinPoint.getArgs()).filter(obj -> obj instanceof List).findFirst().ifPresent(obj -> {
//                    List<Decision> decisions = ((List) obj);
//                    decisions.forEach(this.replicaWebhookCreateService::createWebhooks);
//                });
//            }
//            break;
//            default:
////                log.debug("Replica webhook adm event type has not implimented logic");
//        }
//        return result;
//    }
//
//
//}
