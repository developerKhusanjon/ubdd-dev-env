package uz.ciasev.ubdd_service.dto.internal.response;

import lombok.Data;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;


@Data
public class UploadFileDTO {
    private final String path;
    private final LocalFileUrl url;

    public UploadFileDTO(String path) {
        this.path = path;
        this.url = new LocalFileUrl(path);
    }

    public static UploadFileDTO ofNullable(String path) {
        if (path == null) return null;
        return new UploadFileDTO(path);
    }
}
