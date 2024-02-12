package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.dto.internal.request.mib.MibReturnRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.SendResponse;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

public interface RequestReturnFromMibService {

    SendResponse sendReturnRequest(@Inspector User user, Long cardId, MibReturnRequestDTO requestDTO);

}
