package privatesns.capstone.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class PostRequest {

    public record Create(
        @NotNull MultipartFile image,
        @NotNull Long groupId) {
    }
}
