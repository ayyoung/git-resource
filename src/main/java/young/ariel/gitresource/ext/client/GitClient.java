package young.ariel.gitresource.ext.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import young.ariel.gitresource.ext.dto.ExtRepo;
import young.ariel.gitresource.ext.dto.ExtUser;

import java.util.List;

@FeignClient(value="git", url="https://api.github.com")
public interface GitClient {
    @GetMapping("/users/{user}")
    ResponseEntity<ExtUser> getUserData(@PathVariable String user);

    @GetMapping("/users/{user}/repos")
    ResponseEntity<List<ExtRepo>> getUserRepos(@PathVariable String user);
}
