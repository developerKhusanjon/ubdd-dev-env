package uz.ciasev.ubdd_service.utils;

import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleBodyType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassDataAbstract;

import java.util.List;
import java.util.Optional;

public class UbddFormatUtils {

//    public static String buildTransport(ProtocolUbddDataView ubddData) {
//        return String.join(", ", List.of(
//                Optional.ofNullable(ubddData.getVehicleBodyType()).map(AbstractDict::getDefaultName).orElse("yo'q"),
//                Optional.ofNullable(ubddData.getVehicleNumber()).orElse("yo'q"),
//                Optional.ofNullable(ubddData.getVehicleBrand()).orElse("yo'q"),
//                Optional.ofNullable(ubddData.getVehicleColorType()).map(AbstractDict::getDefaultName).orElse("yo'q")
//        ));
//    }

    public static String buildTransport(Protocol protocol, UbddTexPassDataAbstract ubddData) {
        return String.join(", ", List.of(
                Optional.ofNullable(ubddData.getVehicleBodyType()).map(UBDDVehicleBodyType::getDefaultName).orElse("yo'q"),
                Optional.ofNullable(protocol.getVehicleNumber()).orElse("yo'q"),
                Optional.ofNullable(ubddData.getVehicleBrand()).orElse("yo'q"),
                Optional.ofNullable(ubddData.getVehicleColorType()).orElse("yo'q")
        ));
    }
}
