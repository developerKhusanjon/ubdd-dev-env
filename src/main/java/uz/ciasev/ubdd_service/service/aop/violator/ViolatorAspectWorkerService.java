package uz.ciasev.ubdd_service.service.aop.violator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.manzil.ManzilService;
import uz.ciasev.ubdd_service.mvd_core.api.tax.TaxService;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.main.PersonDataService;
import uz.ciasev.ubdd_service.service.violator.ViolatorDetailService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViolatorAspectWorkerService {

    private final ExecutorService mainPool = Executors.newFixedThreadPool(12);

    private final ViolatorDetailService violatorDetailService;
    private final ViolatorService violatorService;
    private final ManzilService manzilService;
    private final TaxService taxService;
    private final PersonDataService personDataService;

    public void processViolator(Long violatorId, boolean updatePhoto, UUID traceId) {

        if (violatorId == null) {
            return;
        }

        Violator violator = violatorService.getById(violatorId);

        if (!isRealPinpp(violator)) {
            return;
        }
        mainPool.submit(new Worker(violator, updatePhoto, traceId));
    }

    public void processViolatorSync(Violator violator, boolean updatePhoto) {

        if (!isRealPinpp(violator)) {
            return;
        }
        new Worker(violator, updatePhoto, null).run();
    }

    private boolean isRealPinpp(Violator violator) {

        Boolean isRealPinpp = Optional.ofNullable(violator)
                .map(Violator::getPerson)
                .map(Person::isRealPinpp)
                .orElse(null);

        if (isRealPinpp == null || !isRealPinpp) {
            return false;
        }
        return true;
    }

    private class Worker implements Runnable {

        private final Violator violator;
        private final boolean updatePhoto;
        private final UUID traceId;

        public Worker(Violator violator, boolean updatePhoto, UUID traceId) {
            this.violator = violator;
            this.updatePhoto = updatePhoto;
            this.traceId = traceId;
        }

        @SneakyThrows
        @Override
        public void run() {

            log.debug("ASYNC SERVICE CALL START {}, TRACE-ID:{} (QWAS)",
                    LocalDateTime.now(),
                    traceId);

//            Violator violatorDB = violatorService.getById(violator.getId());
            Violator violatorDB = violator;

            CompletableFuture<Address> futureManzil = CompletableFuture.supplyAsync(() -> getManzilAddress(violatorDB)).handle((r, e) -> r);
            CompletableFuture<String> futureInn = CompletableFuture.supplyAsync(() -> getPersonInn(violatorDB)).handle((r, e) -> r);

            CompletableFuture<Void> manzilUpdate = futureManzil.thenAcceptAsync((address) -> updateManzil(violatorDB, address));
            CompletableFuture<Void> innUpdate = futureInn.thenAcceptAsync((inn) -> updateViolatorInn(violatorDB, inn));

            if (updatePhoto) {
                CompletableFuture<String> futurePhoto = CompletableFuture.supplyAsync(() -> getPhotoUri(violatorDB)).handle((r, e) -> r);
                CompletableFuture<Void> photoUpdate = futurePhoto.thenAcceptAsync((photo) -> updateViolatorPhoto(violatorDB, photo));
                photoUpdate.get();
            }

            manzilUpdate.get();
            innUpdate.get();

//            String inn = futureInn.get();
//            String photoUri = futurePhoto.get();
//            CompletableFuture<Void> violatorUpdate = CompletableFuture.runAsync(() -> updateViolator(violatorDB, inn, photoUri));
//
//            manzilUpdate.get();
//            violatorUpdate.get();

            log.debug("ASYNC SERVICE CALL END {}, TRACE-ID:{} (QWAS)",
                    LocalDateTime.now(),
                    traceId);
        }

        private Address getManzilAddress(Violator violatorDB) {

            Long start = System.currentTimeMillis();

            Optional<Address> rsl = manzilService.findAddressByPinpp(violatorDB.getPerson().getPinpp());

            logServiceCallEnd(start, "MANZIL", String.valueOf(rsl));

            return rsl.orElse(null);
        }

        private String getPersonInn(Violator violatorDB) {

            Long start = System.currentTimeMillis();

            String rsl = taxService.getPersonInnByPinpp(violatorDB.getPerson().getPinpp());

            logServiceCallEnd(start, "TAX", rsl);

            return rsl;
        }

        private String getPhotoUri(Violator violatorDB) {

            Long start = System.currentTimeMillis();

            String photoUri = personDataService.getPhotoByPinpp(violatorDB.getPerson().getPinpp());

            logServiceCallEnd(start, "PHOTO", photoUri);

            return photoUri;
        }

        private void updateManzil(Violator violatorDB, Address manzilAddress) {

            if (manzilAddress == null) {
                return;
            }

            try {
                violatorDetailService.setResidenceAddressForAll(violatorDB, manzilAddress);
            } catch (Exception e) {
                log.error("ViolatorAspectWorker failed setting manzil for violator {}", violatorDB.getId(), e);
            }

//            List<ViolatorDetail> violatorDetail = violatorDetailService.findByViolatorId(violatorDB.getId());
//            Address savedManzilAddress = addressService.create(manzilAddress);
//            violatorDetail.forEach((vd) -> vd.setResidenceAddress(savedManzilAddress));
//            violatorDetailService.saveAll(violatorDetail);
        }

//        private void updateViolator(Violator violatorDB,
//                                    String personInn,
//                                    String photoUri) {
//
//            boolean violatorDBUpdate = false;
//
//            if (personInn != null) {
//                violatorDB.setInn(personInn);
//                violatorDBUpdate = true;
//            }
//            if (updatePhoto) {
//                if (photoUri != null) {
//                    violatorDB.setPhotoUri(photoUri);
//                    violatorDBUpdate = true;
//                }
//            }
//            if (violatorDBUpdate) {
//                violatorService.create(violatorDB);
//            }
//    }

        private void updateViolatorPhoto(Violator violatorDB,
                                         String photoUri) {
            if (photoUri == null) {
                return;
            }

            try {
                violatorService.setViolatorPhoto(violatorDB, photoUri);
            } catch (Exception e) {
                log.error("ViolatorAspectWorker failed setting photo for violator {}", violatorDB.getId(), e);
            }

        }

        private void updateViolatorInn(Violator violatorDB,
                                       String personInn) {
            if (personInn == null) {
                return;
            }

            try {
                violatorService.setViolatorInn(violatorDB, personInn);
            } catch (Exception e) {
                log.error("ViolatorAspectWorker failed setting inn for violator {}", violatorDB.getId(), e);
            }
        }

        private void logServiceCallEnd(Long start, String serviceName, String value) {

            Long duration = System.currentTimeMillis() - start;

            log.debug("ASYNC SERVICE {}: {} ({}), TRACE-ID:{} (QWAS)",
                    serviceName,
                    value,
                    duration,
                    traceId);
        }
    }

    @PreDestroy
    private void destroy() {
        mainPool.shutdown();
    }
}
