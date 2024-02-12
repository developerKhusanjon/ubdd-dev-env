package uz.ciasev.ubdd_service.mvd_core.api.autocon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AutoconTokenManagerServiceImpl implements AutoconTokenManagerService {

    private String token;
    private LocalDate tokenExpireDate = LocalDate.MIN;

    @Override
    public String getToken(Supplier<String> tokenSupplier) {
        if (tokenExpireDate.isBefore(LocalDate.now())) {
            refreshToken(tokenSupplier);
        }

        return token;
    }

    @Override
    public void refreshToken(Supplier<String> tokenSupplier) {
        token = tokenSupplier.get();
        tokenExpireDate = LocalDate.now().plusDays(29);
    }


//    private ConcurrentMap<String, String> tokenHolder = new ConcurrentHashMap<>();
//    private ReentrantLock tokenLock = new ReentrantLock();
//
//    @Override
//    public String getToken(Supplier<String> tokenSupplier) {
//
//        return tokenHolder.getOrDefault("token", refreshToken(tokenSupplier));
//    }
//
//    @Override
//    public String refreshToken(Supplier<String> tokenSupplier) {
//
//        if (tokenLock.isLocked()) {
//            tokenLock.lock();
//            tokenLock.unlock();
//        } else {
//            tokenLock.lock();
//            try {
//                tokenHolder.put("token", tokenSupplier.get());
//            } finally {
//                tokenLock.unlock();
//            }
//        }
//        return tokenHolder.get("token");
//    }
}
