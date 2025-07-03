package privatesns.capstone.domain.post.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import privatesns.capstone.domain.post.dto.PostRequest;
import privatesns.capstone.domain.post.dto.PostResponse;
import privatesns.capstone.domain.post.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal Long userId,
                                    @Valid @ModelAttribute PostRequest.Create request) {
        postService.create(userId, request.groupId(), request.image());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PostResponse.Details> getPostsByGroupIdAndCursorId(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long groupId,
            @Nullable @RequestParam PostRequest.Cursor cursor
    ) {
        var result = postService.getPostsByGroupIdAndCursor(userId, groupId, cursor);
        return ResponseEntity.ok(result);
    }
}
