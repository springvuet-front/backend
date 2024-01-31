package prologbackend.dto.teampage;

import lombok.Getter;

import java.util.List;
@Getter
public class TeamScheduleDto {
    private List<TeamScheduleDetailDto> keySchedules;
    private List<TeamScheduleDetailDto> monthSchedules;

    public TeamScheduleDto(List<TeamScheduleDetailDto> keySchedules, List<TeamScheduleDetailDto> monthSchedules) {
        this.keySchedules = keySchedules;
        this.monthSchedules = monthSchedules;
    }
}
