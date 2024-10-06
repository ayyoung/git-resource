package young.ariel.gitresource.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import young.ariel.gitresource.dto.GitUserDTO;
import young.ariel.gitresource.ext.client.GitClient;
import young.ariel.gitresource.ext.dto.ExtRepo;
import young.ariel.gitresource.ext.dto.ExtUser;

import java.util.Date;
import java.util.List;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitResourceServiceTest {

    private GitResourceService service;
    private GitClient client;
    private final Random randomGen = new Random();
    private final List<String> expectedUserHeaders = List.of(
            "user_name",
            "display_name",
            "avatar",
            "geo_location",
            "email",
            "url",
            "created_at",
            "repos"

    );
    private final List<String> expectedRepoHeaders =
            List.of("name", "url");

    private String userName;

    @BeforeEach
    void setup() {
        client = mock(GitClient.class);
        service = new GitResourceService(client);
        String repoName = "TestRepo$" + randomGen.nextDouble();
        userName = "TestUser$" + randomGen.nextDouble();
        when(client.getUserRepos(anyString())).thenReturn(
                ResponseEntity.of(Optional.of(List.of(
                        ExtRepo.builder()
                                .name(repoName)
                                .htmlUrl("https://example.com/" + userName)
                                .build()
                )))
        );

        when(client.getUserData(anyString())).thenReturn(
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

    @Test
    void testExpectedFormat() throws Exception {
        GitUserDTO returnValue = service.getDataByUsername(userName);
        ObjectMapper mapper = new ObjectMapper();
        String returnResponse = mapper.writeValueAsString(returnValue);
        for (String expectedUserHeader : expectedUserHeaders) {
            assertTrue(returnResponse.contains(expectedUserHeader));
        }

        String repos = mapper.writeValueAsString(returnValue.getRepos());
        for (String header : expectedRepoHeaders) {
            assertTrue(repos.contains(header));
        }
    }

}