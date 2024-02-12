package uz.ciasev.ubdd_service.service.aop.violator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
@Slf4j
public class ViolatorAspect {

    private final ViolatorAspectWorkerService workerService;

    @Pointcut("execution(* uz.ciasev.ubdd_service.service.main.ActorService.rebindViolator(..))")
    private void violatorRebind() {
    }

    @Around("@annotation(ViolatorUpdateDueToProtocolCreation)")
    public Protocol protocolCreationProxy(ProceedingJoinPoint joinPoint) throws Throwable {

        UUID traceId = UUID.randomUUID();

        log.debug("PROTOCOL CREATE START {}, TRACE-ID:{} (QWAS)",
                LocalDateTime.now(),
                traceId);

        Protocol rsl = (Protocol) joinPoint.proceed();

        log.debug("PROTOCOL CREATE END {} PROTOCOL-ID:{}, TRACE-ID:{} (QWAS)",
                LocalDateTime.now(),
                Optional.ofNullable(rsl).map(Protocol::getId).orElse(null),
                traceId);

        try {
            Long violatorId = rsl.getViolatorDetail().getViolator().getId();
            workerService.processViolator(violatorId, true, traceId);
        } catch (Exception e) {
            log.error("ViolatorAspect failed process violator data for creation of protocol {}", rsl.getId(), e);
        }

        return rsl;
    }

    @Around("violatorRebind()")
    public Violator violatorRebindProxy(ProceedingJoinPoint joinPoint) throws Throwable {

        Violator rsl = (Violator) joinPoint.proceed();

        try {
            workerService.processViolator(rsl.getId(), false, null);
        } catch (Exception e) {
            log.error("ViolatorAspect failed process violator data for rebind violator {}", rsl.getId(), e);
        }

        return rsl;
    }
}
