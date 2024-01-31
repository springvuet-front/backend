package prologbackend.dto.teampage;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeampageDetailResponseDto {
    private String projectName;
    private String teamName;
    private LocalDateTime start;
    private LocalDateTime end;
    private String github;

    public TeampageDetailResponseDto(String projectName, String teamName, LocalDateTime start, LocalDateTime end, String github) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.start = start;
        this.end = end;
        this.github = github;
    }
}
