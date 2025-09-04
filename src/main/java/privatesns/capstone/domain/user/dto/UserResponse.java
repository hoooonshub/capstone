package privatesns.capstone.domain.user.dto;

import java.util.List;

public class UserResponse {

    public record Success(boolean success) {
    }

    public record SearchResult(
            Long userId,
            String loginId
    ) {
    }

    public record SearchResults(
        List<SearchResult> results
    ) {
    }
}
