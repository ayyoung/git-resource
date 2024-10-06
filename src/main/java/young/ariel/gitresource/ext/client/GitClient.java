package young.ariel.gitresource.ext.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import young.ariel.gitresource.ext.dto.ExtRepo;
import young.ariel.gitresource.ext.dto.ExtUser;

import java.util.Collections;
import java.util.List;

@FeignClient(value="git", url="https://api.github.com")
public interface GitClient {
    @GetMapping("/users/{user}")
    ResponseEntity<ExtUser> fetchUserData(@PathVariable String user);

    @GetMapping("/users/{user}/repos")
    ResponseEntity<List<ExtRepo>> fetchUserRepos(@PathVariable String user);

    @Cacheable(value="repos", unless="#result == null")
    default List<ExtRepo> getUserRepos(String user) {
        ResponseEntity<List<ExtRepo>> responseEntity = fetchUserRepos(user);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        } return Collections.emptyList();
    }
}
