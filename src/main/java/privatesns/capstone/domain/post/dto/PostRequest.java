package privatesns.capstone.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class PostRequest {

    public record Create(
            @NotNull MultipartFile image,
            @NotNull Long groupId) {
    }

    public record Cursor(
            LocalDateTime createdAt,
            Long postId
            ) {
    }
}
