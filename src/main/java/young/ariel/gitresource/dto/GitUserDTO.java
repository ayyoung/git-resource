package young.ariel.gitresource.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GitUserDTO {
    private String userName;
    private String displayName;
    private String avatar;
    private String geoLocation;
    private String email;
    private String url;
    private Date createdAt;
    private List<GitRepoDTO> repos;
}
