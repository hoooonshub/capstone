package privatesns.capstone.domain.group.dto;

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

    public record Member(
            Long userId,
            String nickname
    ) {
    }
}
