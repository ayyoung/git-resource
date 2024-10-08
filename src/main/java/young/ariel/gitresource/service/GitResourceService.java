package young.ariel.gitresource.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import young.ariel.gitresource.dto.GitRepoDTO;
import young.ariel.gitresource.dto.GitUserDTO;
import young.ariel.gitresource.ext.client.GitClient;
import young.ariel.gitresource.ext.dto.ExtRepo;
import young.ariel.gitresource.ext.dto.ExtUser;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitResourceService {
    private final GitClient gitClient;
    private final SimpleDateFormat dateFormat;

    public GitResourceService(GitClient gitClient) {
        this.gitClient = gitClient;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public GitUserDTO getDataByUsername(String username) throws RuntimeException {
        GitUserDTO userDTO = this.getProfileDataForUser(username);
        if (userDTO != null) {
            userDTO.setRepos(this.getReposForUser(username));
            return userDTO;
        }
        return null;
    }

    public GitUserDTO getProfileDataForUser(String username) throws RuntimeException {
        ResponseEntity<ExtUser> userResponse = this.gitClient.fetchUserData(username);
        if (userResponse.getStatusCode().is2xxSuccessful() && userResponse.getBody() != null) {
            ExtUser user = userResponse.getBody();
            return GitUserDTO.builder()
                    .userName(user.getLogin())
                    .url(user.getHtmlUrl())
                    .email(user.getEmail())
                    .geoLocation(user.getLocation())
                    .avatar(user.getAvatarUrl())
                    .createdAt(dateFormat.format(user.getCreatedAt()))
                    .displayName(user.getName())
                    .build();
        }
        return null;
    }

    public List<GitRepoDTO> getReposForUser(String username) throws RuntimeException {
        return this.gitClient.getUserRepos(username).stream().map(
                repo -> new GitRepoDTO(repo.getName(), repo.getHtmlUrl())
        ).collect(Collectors.toList());
    }
}
