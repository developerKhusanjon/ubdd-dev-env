package uz.ciasev.ubdd_service.service.publicapi;

public interface PublicApiWebhookWorkerService {

    void startAsync();

    void startSync();

    void checkExecutorWorkFine();
}
