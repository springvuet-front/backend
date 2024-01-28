package prologbackend.dto.teampage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.teampage.Teampage;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TeamRequestDto {
    private String projectName;
    private String teamName;
    private LocalDateTime start;
    private LocalDateTime end;
    private String github;

    public Teampage toEntity() {
        return Teampage.builder()
                .projectName(this.projectName)
                .teamName(this.teamName)
                .start(this.start)
                .end(this.end)
                .github(this.github)
                .status(true)
                .build();
    }

}
