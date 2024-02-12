package uz.ciasev.ubdd_service.service.file;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.notfound.ApplicationFileNotFoundException;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Validated
public interface FileService {

    String save(Category category, @NotNull @ValidFileUri(message = ErrorCode.LOADED_FILE_INVALID) String name, @NotNull(message = ErrorCode.FILE_CONTENT_REQUIRED) byte[] fileContent);

    Optional<byte[]> get(String uri);

    default byte[] getOrThrow(String uri) {
        return get(uri)
                .orElseThrow(() -> new ApplicationFileNotFoundException(uri));
    }

    Optional<byte[]> getOrEmpty(String uri);

    void remove(String key);

    static String uniqueName(String name) {
        return UUID.randomUUID() + "_" + name;
    }

    static String cleanName(String name) {
        return name
                .toLowerCase()
                .replaceAll("[\\s-]", "_")
                .replaceAll("[^a-z,0-9._]", "");
    }

    default String extractFileNameFromPath(String path) {
        String[] strings = path.split("/");
        if (strings.length == 1) {
            return path;
        }
        return strings[strings.length - 1];
    }
}
