package prologbackend.dto.mypage;

import lombok.Getter;

import java.util.List;

@Getter
public class MyTeamResponseDto {
    private List<MyTeamResponseDetailDto> currentTeams;
    private List<MyTeamResponseDetailDto> completedTeams;

    public MyTeamResponseDto(List<MyTeamResponseDetailDto> currentTeams, List<MyTeamResponseDetailDto> completedTeams) {
        this.currentTeams = currentTeams;
        this.completedTeams = completedTeams;
    }
}
