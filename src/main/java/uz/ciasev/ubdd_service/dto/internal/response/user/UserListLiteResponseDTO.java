package uz.ciasev.ubdd_service.dto.internal.response.user;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.user.User;

@Getter
public class UserListLiteResponseDTO {

    private final Long id;
    private final String firstNameLat;
    private final String secondNameLat;
    private final String lastNameLat;
    private final String username;
    private final String workCertificate;

    public UserListLiteResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstNameLat = user.getFirstNameLat();
        this.secondNameLat = user.getSecondNameLat();
        this.lastNameLat = user.getLastNameLat();
        this.workCertificate = user.getWorkCertificate();
    }
}
