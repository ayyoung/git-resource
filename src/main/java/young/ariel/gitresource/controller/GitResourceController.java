package young.ariel.gitresource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import young.ariel.gitresource.service.GitResourceService;

@RestController
public class GitResourceController {
    private final GitResourceService service;

    @Autowired
    public GitResourceController(GitResourceService service) {
        this.service = service;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getDataForUser(@PathVariable String username) {
        try {
            return ResponseEntity.ofNullable(this.service.getDataByUsername(username));
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().body("Unable to fetch requested data, please try again later.");
        }
    }
}
