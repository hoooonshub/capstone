package privatesns.capstone.domain.group.membership;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import privatesns.capstone.common.entity.BaseEntity;
import privatesns.capstone.domain.group.Group;

@Getter
@Entity
public class GroupMember extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private Long userId;
}
