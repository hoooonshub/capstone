package privatesns.capstone.domain.post.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import privatesns.capstone.common.config.SecurityConfig;
import privatesns.capstone.common.security.jwt.JwtParser;
import privatesns.capstone.domain.post.service.PostService;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtParser jwtParser;

    @MockitoBean
    private PostService postService;

    @Test
    void 포스트_생성_테스트() throws Exception {
        // given
        Long userId = 1L;
        Long groupId = 1L;

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.png",
                MediaType.IMAGE_PNG_VALUE,
                "dummy image".getBytes()
        );

        TestingAuthenticationToken auth = new TestingAuthenticationToken(userId, null);
        auth.setAuthenticated(true);
        Mockito.when(jwtParser.parseAuthentication(Mockito.anyString())).thenReturn(auth);

        Mockito.doNothing().when(postService).create(userId, groupId, file);

        // when then
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/posts")
                        .file(file)
                        .param("groupId", groupId.toString())
                        .with(authentication(auth))
                        .header("Authorization", "something token")
                )
                .andExpect(status().isCreated());

        verify(postService).create(userId, groupId, file);
    }
}
