package prologbackend.dto.teampage;

import lombok.Getter;

import java.time.LocalDateTime;

//constructor 2개로 -> 주요일정 dto / 모든 일정 dto나눠서
@Getter
public class TeamScheduleDetailDto {
    private String scheduleContent;
    private LocalDateTime scheduleStart;
    private LocalDateTime scheduleEnd;

    public TeamScheduleDetailDto(String scheduleContent, LocalDateTime scheduleStart, LocalDateTime scheduleEnd) {
        this.scheduleContent = scheduleContent;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
    }

    public TeamScheduleDetailDto(String scheduleContent, LocalDateTime scheduleEnd) {
        this.scheduleContent = scheduleContent;
        this.scheduleEnd = scheduleEnd;
    }
}
