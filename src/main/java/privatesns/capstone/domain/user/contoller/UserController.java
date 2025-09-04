package privatesns.capstone.domain.user.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import privatesns.capstone.core.security.model.Tokens;
import privatesns.capstone.domain.user.dto.UserRequest;
import privatesns.capstone.domain.user.dto.UserResponse;
import privatesns.capstone.domain.user.service.AuthService;
import privatesns.capstone.domain.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.Join request) {
        userService.join(request.loginId(), request.password(), request.name());
        return ResponseEntity.ok().body(new UserResponse.Success(true));
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@Valid @RequestBody UserRequest.Login request) {
        return ResponseEntity.ok(authService.login(request.loginId(), request.password()));
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponse.SearchResults> searchByLoginId(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchByLoginId(keyword));
    }
}
