package uz.ciasev.ubdd_service.mvd_core.api.signature.service;

import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateCreateRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateCreateResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.signature.dto.CertificateDetailResponseDTO;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;


//@Service
//@Primary
//@Profile({"local", "test"})
public class SignatureApiServiceMock implements SignatureApiService {

    private final Random random = new Random();

    @Override
    public CertificateCreateResponseDTO createCertificate(CertificateCreateRequestDTO requestDTO) {
        CertificateCreateResponseDTO res = new CertificateCreateResponseDTO();
        res.setCertificate("MIICdTCCAh2gAwIBAgIMOTWsjCBmW2z42Ko3MA8GCyqGXAMPAQECAgICBQAwfjELMAkGA1UEBhMCVVoxNDAyBgNVBAcMK1Rvc2hrZW50IDEwMDAyOSwgWXUuUmFqYWJpeSBrb8q7Y2hhc2ksIDEtdXkxOTA3BgNVBAMMME/Ku3piZWtpc3RvbiBSZXNwdWJsaWthc2kgSWNoa2kgSXNobGFyIFZhemlybGlnaTAeFw0yMjAyMDEwNjI1MzlaFw0yNDAyMDEwNjI1MzlaMA0xCzAJBgNVBAYTAlVaMGAwGQYJKoZcAw8BAQIBMAwGCiqGXAMPAQECAQIDQwAEQCr1h5f77hTzck2y1RFIkCW4DmSb6o9s6+tWjeZdxKBW1pSbiombKdjiaAStU1J3Fj5kOUlzBSVF81RqfZaJKASjgeUwgeIwCQYDVR0TBAIwADAdBgNVHQ4EFgQU3Twsw9SH+CiJUA43wb7EzbTQCikwgbUGA1UdIwSBrTCBqoAUySLcWIdjwUzssmNvWtQD1q5n9SShgYOkgYAwfjELMAkGA1UEBhMCVVoxNDAyBgNVBAcMK1Rvc2hrZW50IDEwMDAyOSwgWXUuUmFqYWJpeSBrb8q7Y2hhc2ksIDEtdXkxOTA3BgNVBAMMME/Ku3piZWtpc3RvbiBSZXNwdWJsaWthc2kgSWNoa2kgSXNobGFyIFZhemlybGlnaYIMT1eO8YRv8SOcmnYfMA8GCyqGXAMPAQECAgICBQADQQBxda0ENiVGD1SQ0hcw3+sGdZnKGQH24HHBuAimM7h2zJXQlxyP1s11vl/dyP0cgLSDiP+Uh5sscUf30Mu8Khsy");
        res.setSerial(UUID.randomUUID().toString().substring(0, 32));
        res.setIssuedOn(LocalDateTime.now());
        res.setExpiresOn(LocalDateTime.now().plusYears(1));
        res.setPassword("2380675");
        return res;
    }

    @Override
    public CertificateDetailResponseDTO getCertificate(String serial) {
        CertificateDetailResponseDTO res = new CertificateDetailResponseDTO();
        res.setPrivateKey("bBpxidCJo156F6+NZXe/fibpooelvTpOVzfthXqBNLlisQaCT3U985YGdsGNmhTyh78q96wpfY78SQuixExAXhTNaGk/qvaP9w9qOymQNrE5FY6HrZ6vNrCbp9n51JBs0zNtUwRtnYti/V8isDDgvkTDf2suWxD3yoUanIprKt+ARR5sPWW7qmPPf7RsbSiBm8HB2YMcL7kceBER2wFDEmXhpOPVcXcPH3xr+9I=");
        res.setSerial(serial);
        res.setIssuedOn(LocalDateTime.now());
        res.setExpiresOn(LocalDateTime.now().plusYears(1));
        res.setOwnerName("Mock owner name");
        return res;
    }

    @Override
    public void pauseCertificate(String serial, String reason) {
        return;
    }

    @Override
    public void resumeCertificate(String serial, String reason) {
        return;
    }

    @Override
    public void revokeCertificate(String serial, String reason) {
        return;
    }

    @Override
    public boolean verifySignatureByCertificate(String serial, String digest, String signature) {
        return random.nextBoolean();
    }

    @Override
    public String extractSignature(String signature) {
        return UUID.randomUUID().toString();
    }

    @Override
    public String makeDigestOfString(String data) {
        return UUID.randomUUID().toString();
    }
}
