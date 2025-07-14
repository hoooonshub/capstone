package privatesns.capstone.domain.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import privatesns.capstone.core.exception.exception.GroupException;
import privatesns.capstone.domain.group.Group;
import privatesns.capstone.domain.group.GroupRepository;
import privatesns.capstone.domain.group.dto.GroupResponse;
import privatesns.capstone.domain.user.service.UserService;

import static privatesns.capstone.core.exception.exception.ExceptionCode.GROUP_NOT_FOUND;
import static privatesns.capstone.core.exception.exception.ExceptionCode.IS_NOT_GROUP_MEMBER;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {
    private final UserService userService;

    private final GroupRepository groupRepository;

    public void create(Long hostId, String groupName) {
        Group group = new Group(hostId, groupName);

        String username = userService.findUsernameById(hostId);
        group.joinMember(hostId, username);

        groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    public GroupResponse.Members getGroupMembers(Long groupId, Long userId) {
        Group group = findGroup(groupId);
        validateUserBelongToGroup(userId, groupId);

        return GroupResponse.Members.from(group.getMembers());
    }

    @Transactional(readOnly = true)
    public void validateGroup(Long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GROUP_NOT_FOUND));
    }

    public void validateUserBelongToGroup(Long userId, Long groupId) {
        Group group = findGroup(groupId);

        if (!group.isMember(userId)) {
            throw new GroupException(IS_NOT_GROUP_MEMBER);
        }
    }

    private Group findGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GROUP_NOT_FOUND));
    }
}
