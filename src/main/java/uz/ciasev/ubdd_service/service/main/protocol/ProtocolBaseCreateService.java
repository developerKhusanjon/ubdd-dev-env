package uz.ciasev.ubdd_service.service.main.protocol;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ProtocolBaseCreateService {

    Protocol createProtocol(User user,
                            ProtocolRequestDTO protocolDTO,
                            Supplier<AdmCase> admCaseSupplier,
                            Function<ViolatorDetail, String> fabulaBuilder);


    default Protocol createProtocol(User user,
                            ProtocolRequestDTO protocolDTO,
                            Supplier<AdmCase> admCaseSupplier) {
        return createProtocol(
                user,
                protocolDTO,
                admCaseSupplier,
                violatorDetail -> protocolDTO.getFabula()
        );
    }
}