package privatesns.capstone.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    @Query("SELECT gm.group FROM GroupMember gm WHERE gm.userId = :userId")
    List<Group> findGroupsByUserId(Long userId);
}
