package prologbackend.dto.teampage;

import lombok.Getter;

import java.util.List;

//members list + schedules list + (프로젝트 시작 , 끝 날짜 + 깃허브 링크(str))
@Getter
public class TeampageResponseDto {
    private List<TeamMembersDto> teamMembersDtos;
    private TeamScheduleDto teamScheduleDto;
    private TeampageDetailResponseDto teampageDetailResponseDto;

    public TeampageResponseDto(List<TeamMembersDto> teamMembersDtos, TeamScheduleDto teamScheduleDto, TeampageDetailResponseDto teampageDetailResponseDto) {
        this.teamMembersDtos = teamMembersDtos;
        this.teamScheduleDto = teamScheduleDto;
        this.teampageDetailResponseDto = teampageDetailResponseDto;
    }
}
