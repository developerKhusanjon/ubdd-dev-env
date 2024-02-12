package uz.ciasev.ubdd_service.service.pdf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.document.Document;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.document.DocumentService;
import uz.ciasev.ubdd_service.service.file.FileService;
import uz.ciasev.ubdd_service.utils.ImageUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PdfModelPhotoServiceImpl implements PdfModelPhotoService {

    private final FileService fileService;
    private final DocumentService documentService;

    private final static int ORGAN_LOGO_HEIGHT = 100;
    private final static int SCENE_PHOTO_HEIGHT = 200;
    private final static int VIOLATOR_PHOTO_HEIGHT = 200;

    @Override
    public String getProtocolOrganPhoto(Protocol protocol) {

        return getFileFromStorage(protocol.getOrgan().getLogoPath());
    }

    @Override
    public String getViolatorProto(Violator violator) {
        return getFileFromStorage(violator.getPhotoUri(), VIOLATOR_PHOTO_HEIGHT);

    }

    @Override
    public String getResolutionOrganLogo(Resolution resolution) {
        return getFileFromStorage(resolution.getOrgan().getLogoPath());
    }

    @Override
    public List<String> getScenePhotos(Long admCaseId) {
        return documentService
                .findScenePhotosByAdmCase(admCaseId)
                .stream()
                .map(Document::getUrl)
                .map(url -> getFileFromStorage(url, SCENE_PHOTO_HEIGHT))
                .limit(2)
                .collect(toList());
    }

    @Override
    public String getUserOrganLogo(User user) {
        return getFileFromStorage(Optional.ofNullable(user.getOrgan()).map(Organ::getLogoPath).orElse(null));
    }

    private String getFileFromStorage(String path, int toHeight) {
        if (path == null)
            return null;

        Optional<byte[]> optionalBytes = fileService.getOrEmpty(path);

        if (optionalBytes.isEmpty()) {
            return null;
        }

        byte[] img;

        try {
            img = ImageUtils.compress(optionalBytes.get(), toHeight);
        } catch (IOException e) {
            img = optionalBytes.get();
        }

        return ImageUtils.convertByteToBase64(img);
    }

    private String getFileFromStorage(String path) {
        if (path == null)
            return null;

        Optional<byte[]> optionalBytes = fileService.getOrEmpty(path);

        if (optionalBytes.isEmpty()) {
            return null;
        }

        byte[] img = optionalBytes.get();

        return ImageUtils.convertByteToBase64(img);
    }

}
