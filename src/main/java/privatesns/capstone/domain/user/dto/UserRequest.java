package privatesns.capstone.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRequest {

    public record Login(
            @NotBlank String loginId,
            @NotBlank String password
    ) {
    }

    public record Join(
            @NotBlank String loginId,
            @NotBlank String password,
            @NotBlank String name
    ) {
    }
}
