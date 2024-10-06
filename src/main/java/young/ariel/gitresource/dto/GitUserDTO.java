package young.ariel.gitresource.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GitUserDTO {
    private String userName;
    private String displayName;
    private String avatar;
    private String geoLocation;
    private String email;
    private String url;
    private String createdAt;
    private List<GitRepoDTO> repos;
}
