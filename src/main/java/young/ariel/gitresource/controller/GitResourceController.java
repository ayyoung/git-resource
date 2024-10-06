package young.ariel.gitresource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import young.ariel.gitresource.dto.GitUserDTO;
import young.ariel.gitresource.service.GitResourceService;

@RestController
public class GitResourceController {
    private GitResourceService service;

    @Autowired
    public GitResourceController(GitResourceService service) {
        this.service = service;
    }

    @GetMapping("/{username}")
    public GitUserDTO getDataForUser(@PathVariable String username) {
        return null;
    }
}
