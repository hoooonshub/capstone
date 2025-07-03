package privatesns.capstone.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {

    public record Detail(
        Long postId,
        String imageUrl,
        LocalDateTime createdAt,
        Long userId,
        String nickname
    ) {
    }

    public record Details(
        List<Detail> details,
        boolean hasNext
    ) {
    }
}
