package privatesns.capstone.domain.user.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import privatesns.capstone.core.security.model.Tokens;
import privatesns.capstone.domain.user.dto.UserRequest;
import privatesns.capstone.domain.user.dto.UserResponse;
import privatesns.capstone.domain.user.service.AuthService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.Join request) {
        authService.join(request.loginId(), request.password(), request.name());
        return ResponseEntity.ok().body(new UserResponse.Success(true));
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@Valid @RequestBody UserRequest.Login request) {
        return ResponseEntity.ok(authService.login(request.loginId(), request.password()));
    }
}
