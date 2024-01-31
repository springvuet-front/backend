package prologbackend.dto.mypage;

import lombok.Getter;

import java.util.List;

@Getter
public class MypageResponseDto {
    private MyTeamResponseDto myTeamResponseDto;
    private List<ScheduleResponseDto> scheduleResponseDtos;

    public MypageResponseDto(MyTeamResponseDto myTeamResponseDto, List<ScheduleResponseDto> scheduleResponseDtos) {
        this.myTeamResponseDto = myTeamResponseDto;
        this.scheduleResponseDtos = scheduleResponseDtos;
    }
}
