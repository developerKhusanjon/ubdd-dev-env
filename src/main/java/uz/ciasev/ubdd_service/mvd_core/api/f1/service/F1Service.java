package uz.ciasev.ubdd_service.mvd_core.api.f1.service;

import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.F1Document;
import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.GcpPersonInfo;
import uz.ciasev.ubdd_service.dto.internal.response.F1DocumentListDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.time.LocalDate;
import java.util.List;

public interface F1Service {

    F1Document findByPinpp(String pinpp);

    byte[] getPhotoById(String id, String photoType);

    List<F1DocumentListDTO> getPersonInfoByFilter(User user,
                                                  String pinpp,
                                                  String searchedPinpp,
                                                  String firstNameLat,
                                                  String secondNameLat,
                                                  String lastNameLat,
                                                  LocalDate birthFrom,
                                                  LocalDate birthTo,
                                                  String series,
                                                  String number,
                                                  Boolean fullInfo,
                                                  Boolean isTablet);

    GcpPersonInfo findGcpInfoByPinpp(String pinpp);

    F1DocumentListDTO getF1Document(GcpPersonInfo gcpPersonInfo);

   void clearSearchHistory(User user, Violator violator);
}
