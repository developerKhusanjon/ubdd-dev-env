package uz.ciasev.ubdd_service.utils.deserializer;

import uz.ciasev.ubdd_service.entity.dict.user.UserStatus;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;

public class UserStatusDeserializer extends AbstractDictDeserializer<UserStatus> {

    public UserStatusDeserializer(DictionaryService<UserStatus> service) {
        super(UserStatus.class, service);
    }
}
