package privatesns.capstone.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import privatesns.capstone.domain.group.service.GroupService;
import privatesns.capstone.domain.post.Post;
import privatesns.capstone.domain.post.PostQueryRepository;
import privatesns.capstone.domain.post.PostRepository;
import privatesns.capstone.domain.post.dto.PostRequest;
import privatesns.capstone.domain.post.dto.PostResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final GroupService groupService;
    private final ImageService imageService;

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;

    public void create(Long userId, Long groupId, MultipartFile multipartFile) {
        groupService.validateGroup(groupId);
        groupService.validateUserBelongToGroup(userId, groupId);

        String imageUrl = imageService.saveImage(multipartFile);

        postRepository.save(new Post(imageUrl, userId, groupId));
    }

    public PostResponse.Details getPostsByGroupIdAndCursor(Long userId, Long groupId, PostRequest.Cursor cursor) {
        groupService.validateGroup(groupId);
        groupService.validateUserBelongToGroup(userId, groupId);

        return postQueryRepository.findByGroupIdAndCursor(groupId, cursor);
    }
}
