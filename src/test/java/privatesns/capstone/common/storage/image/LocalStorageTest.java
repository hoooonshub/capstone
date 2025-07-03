package privatesns.capstone.common.storage.image;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "file.upload.root=src/test/resources/test-uploads"
})
class LocalStorageTest {

    @Autowired
    private LocalStorage localStorage;

    @Value("${file.upload.root}")
    private String rootPathString;

    private Path rootPath;
    private String testFilename;
    private Path testImagePath;


    @AfterEach
    void clearDirectory() throws IOException {
        if (Files.exists(rootPath)) {
            Files.walk(rootPath)
                .sorted((a, b) -> -a.compareTo(b))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        System.err.println("Failed to delete: " + path);
                    }
                });
        }
    }

    @Test
    void 사진_업로드_테스트_성공() throws IOException {
        // given
        rootPath = Paths.get(rootPathString);
        testFilename = "test.jpg";
        testImagePath = rootPath.resolve(testFilename);

        // when
        String uploadedPath = localStorage.upload(testFilename);

        // then
        assertTrue(Files.exists(rootPath));
        assertEquals(testImagePath.toString(), uploadedPath);
    }

    @Test
    void 디렉토리_생성_테스트() throws IOException {
        // given
        rootPath = Paths.get(rootPathString);
        testFilename = "test.jpg";
        testImagePath = rootPath.resolve(testFilename);

        // when
        localStorage.upload(testFilename);

        // then
        assertTrue(Files.exists(rootPath));
        assertTrue(Files.isDirectory(rootPath));
    }
}
