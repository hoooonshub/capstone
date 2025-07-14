package privatesns.capstone.domain.group.dto;

import jakarta.validation.constraints.NotBlank;

public class GroupRequest {

    public record Create(
            @NotBlank String groupName
    ) {
    }
}
