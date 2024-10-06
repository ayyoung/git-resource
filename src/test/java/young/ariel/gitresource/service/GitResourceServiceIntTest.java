package young.ariel.gitresource.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AopTestUtils;
import young.ariel.gitresource.ext.client.GitClient;
import young.ariel.gitresource.ext.dto.ExtRepo;
import young.ariel.gitresource.ext.dto.ExtUser;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration
@SpringBootTest
@ExtendWith(SpringExtension.class)
class GitResourceServiceIntTest {

    private GitResourceService mock;

    @Autowired
    private GitResourceService service;
    @Autowired
    private GitClient client;
    private final Random randomGen = new Random();

    private String userName;

    @BeforeEach
    void setup() {
        mock = AopTestUtils.getTargetObject(service);
        String repoName = "TestRepo$" + randomGen.nextDouble();
        userName = "TestUser$" + randomGen.nextDouble();
        when(client.fetchUserRepos(anyString())).thenReturn(
                ResponseEntity.of(
                        Optional.of(
                                List.of(
                                        ExtRepo.builder()
                                                .name(repoName)
                                                .htmlUrl("https://example.com/" + userName)
                                                .build()
                                ))));

        when(client.fetchUserData(anyString())).thenReturn(
                ResponseEntity.of(
                        Optional.of(
                                ExtUser.builder()
                                        .login(userName)
                                        .email(userName + "@example.com")
                                        .name("Test User")
                                        .htmlUrl("https://example.com/" + userName)
                                        .avatarUrl("https://example.com/" + userName)
                                        .createdAt(new Date())
                                        .location("usa")
                                        .build()
                        )
                )
        );
    }


    @EnableCaching
    @Configuration
    public static class CachingTestConfig {
        private GitClient gitClient;

        @Bean
        public GitClient gitClient() {
            if (gitClient == null) {
                gitClient = mock(GitClient.class);
            }
            return gitClient;
        }

        @Bean
        public GitResourceService gitResourceServiceMockImplementation() {
            return new GitResourceService(gitClient());
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("repos");
        }

    }

    @Test
    void givenCachedRepos_AdditionalCallsShouldBeReducedToJustUserProfileFetch() throws Exception {
        assertNotNull(mock.getDataByUsername(userName));
        assertNotNull(mock.getDataByUsername(userName));
        assertNotNull(mock.getDataByUsername(userName));
        verify(client, times(3)).fetchUserData(anyString());
        verify(client, times(1)).getUserRepos(anyString());
    }

}