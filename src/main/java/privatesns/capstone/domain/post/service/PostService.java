package privatesns.capstone.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import privatesns.capstone.domain.group.service.GroupService;
import privatesns.capstone.domain.post.Post;
import privatesns.capstone.domain.post.PostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final GroupService groupService;
    private final ImageService imageService;

    private final PostRepository postRepository;

    public void create(Long userId, Long groupId, MultipartFile multipartFile) {
        groupService.validateGroup(groupId);
        groupService.validateUserBelongToGroup(userId, groupId);

        String imageUrl = imageService.saveImage(multipartFile);

        postRepository.save(new Post(imageUrl, userId, groupId));
    }
}
