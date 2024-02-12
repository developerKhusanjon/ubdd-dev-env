package uz.ciasev.ubdd_service.entity;

import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.user.User;

public interface Inspector extends Place {

    User getUser();

    String getWorkCertificate();

    Region getRegion();

    District getDistrict();

    Organ getOrgan();

    Department getDepartment();

    Position getPosition();

    Rank getRank();

    String getInfo();

    String getFio();
}
