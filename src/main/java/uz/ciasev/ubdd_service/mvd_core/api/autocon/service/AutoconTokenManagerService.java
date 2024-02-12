package uz.ciasev.ubdd_service.mvd_core.api.autocon.service;

import java.util.function.Supplier;

public interface AutoconTokenManagerService {

    String getToken(Supplier<String> tokenSupplier);

    void refreshToken(Supplier<String> tokenSupplier);
}
