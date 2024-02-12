package uz.ciasev.ubdd_service.mvd_core.api.signature.service;

import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateCreateRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateCreateResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateDetailResponseDTO;

public interface SignatureApiService {

    CertificateCreateResponseDTO createCertificate(CertificateCreateRequestDTO requestDTO);

    CertificateDetailResponseDTO getCertificate(String serial);

    void pauseCertificate(String serial, String reason);

    void resumeCertificate(String serial, String reason);

    void revokeCertificate(String serial, String reason);

    boolean verifySignatureByCertificate(String serial, String digest, String signature);

    String extractSignature(String signature);

    String makeDigestOfString(String data);
}
