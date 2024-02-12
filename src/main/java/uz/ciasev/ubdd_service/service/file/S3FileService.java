package uz.ciasev.ubdd_service.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.exception.FileSizeTooLargeException;
import uz.ciasev.ubdd_service.exception.S3ReadFileException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;

@Service
public class S3FileService implements FileService {

    private final String bucketName;
    private final AmazonS3 s3client;
    private final FileFormatService fileFormatService;

    @Autowired
    public S3FileService(@Value("${mvd-ciasev.files.s3.bucket-name}") String bucketName,
                         AmazonS3 s3client, FileFormatService fileFormatService) {
        this.bucketName = bucketName;
        this.s3client = s3client;
        this.fileFormatService = fileFormatService;
    }

    @Override
    public String save(Category category, String name, byte[] fileContent) {

        validate(name, fileContent);

        String key;
        do {
            key = prepareKey(category, name);
        } while (s3client.doesObjectExist(bucketName, key));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileContent.length);
        s3client.putObject(bucketName, key, new ByteArrayInputStream(fileContent), metadata);

        return key;
    }

    @Override
    public Optional<byte[]> get(String uri) {

        try(S3Object obj = s3client.getObject(bucketName, uri)) {

            if (obj == null)
                return Optional.empty();

            return Optional.of(obj
                    .getObjectContent()
                    .readAllBytes());
        } catch (IOException e) {
            throw new S3ReadFileException(e.getMessage());
        }
    }

    @Override
    public Optional<byte[]> getOrEmpty(String uri) {
        try {
            return get(uri);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(String key) {

        boolean fileExists = s3client.doesObjectExist(bucketName, key);
        if (fileExists)
            s3client.deleteObject(bucketName, key);
    }

    private String prepareKey(Category category, String name) {

        Calendar directoryDate = Calendar.getInstance();

        String cleanedName = FileService.uniqueName(FileService.cleanName(name));

        return Paths.get(
                String.valueOf(directoryDate.get(Calendar.YEAR)),
                String.valueOf(directoryDate.get(Calendar.MONTH) + 1),
                String.valueOf(directoryDate.get(Calendar.DAY_OF_MONTH)),
                String.valueOf(category).toLowerCase(),
                cleanedName
        ).toString();
    }

    private void validate(String name, byte[] fileContent) {
        FileFormat fileFormat = fileFormatService.calcForUrl(name);

        if (fileFormat.getMaxSizeInByte() < fileContent.length) {
            throw new FileSizeTooLargeException();
        }
    }
}
