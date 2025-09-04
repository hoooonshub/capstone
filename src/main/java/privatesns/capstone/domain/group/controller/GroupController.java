package privatesns.capstone.domain.group.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import privatesns.capstone.domain.group.dto.GroupRequest;
import privatesns.capstone.domain.group.dto.GroupResponse;
import privatesns.capstone.domain.group.service.GroupService;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody GroupRequest.Create request) {
        groupService.create(userId, request.groupName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<GroupResponse.Members> getMembers(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "id") Long groupId) {
        return ResponseEntity.ok(groupService.getGroupMembers(groupId, userId));
    }

    @GetMapping("/mine")
    public ResponseEntity<GroupResponse.MyGroups> getMyGroups(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(groupService.getMyGroups(userId));
    }
}
