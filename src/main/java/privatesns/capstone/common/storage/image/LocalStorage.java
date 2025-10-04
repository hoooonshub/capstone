package privatesns.capstone.common.storage.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import privatesns.capstone.core.exception.exception.FileException;
import privatesns.capstone.core.storage.image.ImageStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static privatesns.capstone.core.exception.exception.ExceptionCode.FILE_DIRECTORY_CREATED_FAILED;
import static privatesns.capstone.core.exception.exception.ExceptionCode.FILE_UPLOAD_FAILED;

@Component
public class LocalStorage implements ImageStorage {
    private final Path rootPath;

    public LocalStorage(@Value("${file.upload.root}") String rootPath) {
        this.rootPath = Paths.get(rootPath);
    }

    @Override
    public String upload(MultipartFile file, String filename) {
        if (!Files.exists(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                throw new FileException(FILE_DIRECTORY_CREATED_FAILED);
            }
        }

        Path target = rootPath.resolve(filename);

        try {
            file.transferTo(target);
        } catch (IOException e) {
            throw new FileException(FILE_UPLOAD_FAILED);
        }

        return target.toString();
    }
}
