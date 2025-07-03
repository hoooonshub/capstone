package privatesns.capstone.domain.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import privatesns.capstone.core.exception.exception.GroupException;
import privatesns.capstone.domain.group.Group;
import privatesns.capstone.domain.group.GroupRepository;
import privatesns.capstone.domain.group.membership.service.GroupMemberService;

import static privatesns.capstone.core.exception.exception.ExceptionCode.GROUP_NOT_FOUND;
import static privatesns.capstone.core.exception.exception.ExceptionCode.IS_NOT_GROUP_MEMBER;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMemberService groupMemberService;
    private final GroupRepository groupRepository;

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

    Group findGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GROUP_NOT_FOUND));
    }
}
