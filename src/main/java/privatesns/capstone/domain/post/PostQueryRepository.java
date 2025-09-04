package privatesns.capstone.domain.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import privatesns.capstone.domain.post.dto.PostRequest;
import privatesns.capstone.domain.post.dto.PostResponse;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static privatesns.capstone.domain.group.QGroupMember.groupMember;
import static privatesns.capstone.domain.post.QPost.post;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryRepository {
    private static final int LIMIT_PAGE_SIZE = 9;

    private final JPAQueryFactory queryFactory;

    public PostResponse.Details findByGroupIdAndCursor(Long groupId, PostRequest.Cursor cursor) {
        List<PostResponse.Detail> results = queryFactory.select(constructor(PostResponse.Detail.class,
                        post.id,
                        post.imageUrl,
                        post.createdAt,
                        groupMember.userId,
                        groupMember.nickname
                ))
                .from(post)
                .leftJoin(groupMember)
                .on(
                        post.groupId.eq(groupMember.group.id)
                                .and(post.userId.eq(groupMember.userId))
                )
                .where(
                        post.groupId.eq(groupId)
                                .and(cursorCondition(cursor))
                )
                .orderBy(post.createdAt.desc(), post.id.desc())
                .limit(LIMIT_PAGE_SIZE + 1)
                .fetch();

        boolean hasNext = checkHasNext(results);
        if (hasNext) {
            removeLast(results);
        }

        return new PostResponse.Details(results, hasNext);
    }

    private BooleanExpression cursorCondition(PostRequest.Cursor cursor) {
        return cursor == null ? null
                : post.createdAt.lt(cursor.createdAt())
                .or(post.createdAt.eq(cursor.createdAt())
                        .and(post.id.lt(cursor.postId())
                        )
                );
    }

    private boolean checkHasNext(List<PostResponse.Detail> results) {
        return results.size() > LIMIT_PAGE_SIZE;
    }

    private void removeLast(List<PostResponse.Detail> results) {
        results.removeLast();
    }
}
