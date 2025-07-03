package privatesns.capstone.domain.post.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import privatesns.capstone.domain.group.service.GroupService;
import privatesns.capstone.domain.post.Post;
import privatesns.capstone.domain.post.PostRepository;
import privatesns.capstone.domain.post.dto.PostRequest;
import privatesns.capstone.domain.post.dto.PostResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @MockitoBean
    private GroupService groupService;

    private static final Long USER_ID = 1L;
    private static final Long GROUP_ID = 1L;

    @BeforeEach
    void setUp() {
        doNothing().when(groupService).validateGroup(anyLong());
        doNothing().when(groupService).validateUserBelongToGroup(anyLong(), anyLong());

        postRepository.deleteAll();

        LocalDateTime baseTime = LocalDateTime.now();
        for (int i = 0; i < 20; i++) {
            Post post = new Post("image_url_" + i, USER_ID, GROUP_ID);

            try {
                java.lang.reflect.Field createdAtField = Post.class.getSuperclass().getDeclaredField("createdAt");
                createdAtField.setAccessible(true);
                createdAtField.set(post, baseTime.minus((20 - i), ChronoUnit.HOURS));
            } catch (Exception e) {
                throw new RuntimeException("Failed to set fields via reflection", e);
            }

            postRepository.save(post);
        }
    }

    @AfterEach
    void clearAll() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("cursor가 null일 때 최신 게시글 9개를 가져온다")
    void 커서가_널일_떄_최근_게시글_가져오기_테스트() {
        // Given
        PostRequest.Cursor cursor = null;

        // When
        PostResponse.Details response = postService.getPostsByGroupIdAndCursor(USER_ID, GROUP_ID, cursor);

        // Then
        assertThat(response.details()).hasSize(9);
        assertThat(response.hasNext()).isTrue();

        // 시간 역순 정렬 검증
        List<PostResponse.Detail> details = response.details();
        for (int i = 0; i < details.size() - 1; i++) {
            assertThat(details.get(i).createdAt()).isAfterOrEqualTo(details.get(i + 1).createdAt());
        }
    }

    @Test
    @DisplayName("cursor 값이 있을 때 10번째부터 9개의 게시글을 가져온다")
    void 커서가_있을_때_커서_다음_값부터_가져오기_테스트() {
        // Given
        PostResponse.Details firstPage = postService.getPostsByGroupIdAndCursor(USER_ID, GROUP_ID, null);
        PostResponse.Detail lastPostOfFirstPage = firstPage.details().get(firstPage.details().size() - 1);
        PostRequest.Cursor cursor = new PostRequest.Cursor(lastPostOfFirstPage.createdAt(), lastPostOfFirstPage.postId());

        // When
        PostResponse.Details response = postService.getPostsByGroupIdAndCursor(USER_ID, GROUP_ID, cursor);

        // Then
        assertThat(response.details()).hasSize(9);
        assertThat(response.hasNext()).isTrue();

        // Verify posts are ordered by createdAt desc
        List<PostResponse.Detail> details = response.details();
        for (int i = 0; i < details.size() - 1; i++) {
            assertThat(details.get(i).createdAt()).isAfterOrEqualTo(details.get(i + 1).createdAt());
        }

        assertThat(details.get(0).createdAt()).isBeforeOrEqualTo(lastPostOfFirstPage.createdAt());
    }

    @Test
    @DisplayName("마지막 페이지에서 hasNext가 false인 경우에도 게시글을 정상적으로 가져온다")
    void 마지막_페이지에서_hasNext가_false일때_테스트() {
        // Given
        PostResponse.Details firstPage = postService.getPostsByGroupIdAndCursor(USER_ID, GROUP_ID, null);
        PostResponse.Detail lastPostOfFirstPage = firstPage.details().get(firstPage.details().size() - 1);
        PostRequest.Cursor firstCursor = new PostRequest.Cursor(lastPostOfFirstPage.createdAt(), lastPostOfFirstPage.postId());

        PostResponse.Details secondPage = postService.getPostsByGroupIdAndCursor(USER_ID, GROUP_ID, firstCursor);
        PostResponse.Detail lastPostOfSecondPage = secondPage.details().get(secondPage.details().size() - 1);
        PostRequest.Cursor secondCursor = new PostRequest.Cursor(lastPostOfSecondPage.createdAt(), lastPostOfSecondPage.postId());

        // When
        PostResponse.Details response = postService.getPostsByGroupIdAndCursor(USER_ID, GROUP_ID, secondCursor);

        // Then
        assertThat(response.details()).hasSize(2); // 20개 [9개] 첫 페이지, [9개] 둘째 페이지, 나머지 2개
        assertThat(response.hasNext()).isFalse();

        // Verify posts are ordered by createdAt desc
        List<PostResponse.Detail> details = response.details();
        for (int i = 0; i < details.size() - 1; i++) {
            assertThat(details.get(i).createdAt()).isAfterOrEqualTo(details.get(i + 1).createdAt());
        }

        assertThat(details.get(0).createdAt()).isBefore(lastPostOfSecondPage.createdAt());
    }
}
