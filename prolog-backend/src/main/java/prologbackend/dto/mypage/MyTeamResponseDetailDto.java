package prologbackend.dto.mypage;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyTeamResponseDetailDto {
    private String teamPosition;
    private String teamRole;
    private String projectName;
    private String teamName;
    private LocalDateTime start;
    private LocalDateTime end;


    public MyTeamResponseDetailDto(String teamPosition, String teamRole, String projectName, String teamName, LocalDateTime start, LocalDateTime end) {
        this.teamPosition = teamPosition;
        this.teamRole = teamRole;
        this.projectName = projectName;
        this.teamName = teamName;
        this.start = start;
        this.end = end;
    }

}
