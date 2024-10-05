package young.ariel.gitresource.ext.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExtUser {
    private String login;
    private String name;
    private String avatarUrl;
    private String location;
    private String email;
    private String htmlUrl;
}
