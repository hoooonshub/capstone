package privatesns.capstone.domain.group.dto;

import privatesns.capstone.domain.group.Group;
import privatesns.capstone.domain.group.GroupMember;

import java.util.List;

public class GroupResponse {

    public record Members(
            List<Member> members
    ) {
        public static Members from(List<GroupMember> members) {
            return new Members(
                    members.stream()
                            .map(member ->
                                    new Member(member.getUserId(), member.getNickname()))
                            .toList());
        }
    }

    private record Member(
            Long userId,
            String nickname
    ) {
    }

    public record MyGroups(
            List<GroupInfoSummary> groupInfos
    ) {
        public static MyGroups from(List<Group> groups) {
            return new MyGroups(
                    groups.stream()
                            .map(group ->
                                    new GroupInfoSummary(group.getId(), group.getName())
                            ).toList()
            );
        }
    }

    private record GroupInfoSummary(
            Long groupId,
            String groupName
    ) {
    }
}
