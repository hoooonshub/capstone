package privatesns.capstone.core.storage.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorage {
    String upload(MultipartFile file, String filename);
}
