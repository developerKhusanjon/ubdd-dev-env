package uz.ciasev.ubdd_service.event.subscribers.autocon;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.event.subscribers.PunishmentStatusChangeSubscriber;
import uz.ciasev.ubdd_service.service.autocon.AutoconPunishmentService;

//@Service
@RequiredArgsConstructor
public class DeleteFromAutoconEventSubscriber extends PunishmentStatusChangeSubscriber {

    private final AutoconPunishmentService service;

    @Override
    public void apply(Punishment punishment, ExecutorType executorType) {
        if (punishment.getStatus().is(AdmStatusAlias.EXECUTED)) {
            service.deletePunishmentFromAutocon(punishment);
        }
    }
}
