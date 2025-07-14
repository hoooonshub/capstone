package privatesns.capstone.domain.group;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import privatesns.capstone.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {
    private String name;

    private Long hostId;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<GroupMember> members;

    public Group(Long hostId, String name) {
        this.hostId = hostId;
        this.name = name;
        members = new ArrayList<>();
    }

    /*
        그룹원이 대규모 데이터가 될 시 members를 다 로딩해서 찾는 것보다
        GroupMember 엔티티에서 existsByGroupAndUser처럼
        DB에 직접 쿼리를 통해 찾는 게 더 나을 수 있음.
        일단은 성능보다는 OOP 관점으로 집중.
     */
    public boolean isMember(Long memberId) {
        return members.stream()
                .anyMatch(member -> member.getUserId().equals(memberId));
    }

    public void joinMember(Long memberId, String memberName) {
        this.members.add(new GroupMember(this, memberId, memberName));
    }
}
