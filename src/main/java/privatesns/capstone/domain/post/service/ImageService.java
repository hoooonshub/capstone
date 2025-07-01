package privatesns.capstone.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import privatesns.capstone.core.exception.exception.ImageException;
import privatesns.capstone.core.storage.image.ImageStorage;

import java.util.Set;
import java.util.UUID;

import static privatesns.capstone.core.exception.exception.ExceptionCode.INVALID_IMAGE_FILE;
import static privatesns.capstone.core.exception.exception.ExceptionCode.NOT_ALLOWED_EXTENSION;

@Service
@RequiredArgsConstructor
public class ImageService {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    private final ImageStorage imageStorage;

    public String saveImage(MultipartFile file) {
        String original = file.getOriginalFilename();

        String ext = extractExtension(original);
        String filename = UUID.randomUUID() + "." + ext;

        return imageStorage.upload(filename);
    }

    private String extractExtension(String original) {
        if (original == null || !original.contains(".")) {
            throw new ImageException(INVALID_IMAGE_FILE);
        }

        String ext = original.substring(original.lastIndexOf(".") + 1);
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new ImageException(NOT_ALLOWED_EXTENSION);
        }

        return ext;
    }
}
