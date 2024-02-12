package uz.ciasev.ubdd_service.service.violation_event.build;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.utils.FioUtils;
import uz.ciasev.ubdd_service.utils.MonthConverter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ViolationEventFabulaBuildingServiceImpl implements ViolationEventFabulaBuildingService {

    private final ViolationTypeDescriptionBuildingService violationTypeService;

    public String build(ViolationEventApiDTO violationEvent, ViolatorCreateRequestDTO violatorRequest, Person person, UbddTexPassDTOI techPassData) {
        String template = "${eventYear} yil ${eventDay} ${eventMonth} kuni soat ${eventTime} da ${eventRegion} ${eventDistrict} " +
                "${eventAddress} ko‘chalari (chorrahasi)da, ${violatorAddress} da ro‘yixatda turuvchi ${violatorInfo} fuqaro " +
                "${vehicleModel} rusumli, ${vehicleNumber} davlat raqam belgili transport vositasida ${violationDescription}, " +
                "bu bilan o‘zbekiston respublikasi MJtKning ${violationArticle} narazda tutilgan huquqbuzarlik sodir etgan.";

        Map<String, String> data = new HashMap<String, String>();
        putValueInData(data, "eventYear", this::getEventYear, violationEvent.getViolationTime());
        putValueInData(data, "eventMonth", this::getEventMonth, violationEvent.getViolationTime());
        putValueInData(data, "eventDay", this::getEventDay, violationEvent.getViolationTime());
        putValueInData(data, "eventTime", this::getEventTime, violationEvent.getViolationTime());
        putValueInData(data, "eventRegion", this::getEventRegion, violationEvent.getRegion());
        putValueInData(data, "eventDistrict", this::getEventDistrict, violationEvent.getDistrict());
        putValueInData(data, "eventAddress", this::getEventAddress, violationEvent.getAddress());
        putValueInData(data, "violationArticle", this::getViolationArticle, violationEvent.getArticlePart());
        putValueInData(data, "violatorAddress", this::getViolatorAddress, violatorRequest.getActualAddress());
        putValueInData(data, "violatorInfo", this::getViolatorInfo, person);
        putValueInData(data, "vehicleModel", this::getVehicleModel, techPassData.getVehicleModel());
        putValueInData(data, "vehicleNumber", this::getVehicleNumber, techPassData.getVehicleNumber());
        putValueInData(data, "violationDescription", violationTypeService::getViolationTypeDescription, violationEvent);

        return StringSubstitutor.replace(template, data);
    }

    private String getEventYear(@NotNull LocalDateTime time) {
        return String.valueOf(time.getYear());
    }

    private String getEventMonth(@NotNull LocalDateTime time) {
        return MonthConverter.valueOf(time.getMonth().toString()).getValue().toLowerCase();
    }

    private String getEventDay(@NotNull LocalDateTime time) {
        return String.valueOf(time.getDayOfMonth());
    }

    private String getEventTime(@NotNull LocalDateTime time) {
        return time.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String getEventRegion(@NotNull Region region) {
        return region.getDefaultName();
    }

    private String getEventDistrict(@NotNull District district) {
        return district.getDefaultName();
    }

    private String getEventAddress(@NotNull String address) {
        return address;
    }

    private String getViolationArticle(@NotNull ArticlePart articlePart) {
        return articlePart.getDefaultName();
    }

    private String getViolatorAddress(@NotNull AddressRequestDTO addressRequestDTO) {
        return addressRequestDTO.buildAddress().getFullAddressText();
    }

    private String getViolatorInfo(Person person) {
        String fio = FioUtils.buildFullFio(person.getFirstNameLat(), person.getSecondNameLat(), person.getLastNameLat());
        String birthDate = person.getBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));

        return String.format("%s (%s)", fio, birthDate);
    }

    private String getVehicleModel(@NotNull String model) {
        return model;
    }

    private String getVehicleNumber(@NotNull String number) {
        return number;
    }

    private <T> void putValueInData(
            Map<String, String> data,
            String key,
            Function<T, String> function,
            @Nullable T value) {

        Optional.ofNullable(value)
                .ifPresentOrElse(
                        time -> data.put(key, function.apply(value)),
                        () -> data.put(key, "---")
                );
    }
}
