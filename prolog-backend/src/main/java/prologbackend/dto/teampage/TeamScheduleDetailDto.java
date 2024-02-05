package prologbackend.dto.teampage;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

//constructor 2개로 -> 주요일정 dto / 모든 일정 dto나눠서
@Getter
public class TeamScheduleDetailDto {
    private UUID scheduleUuid;
    private String scheduleContent;
    private LocalDateTime scheduleStart;
    private LocalDateTime scheduleEnd;

    public TeamScheduleDetailDto(String scheduleContent, LocalDateTime scheduleStart, LocalDateTime scheduleEnd, UUID scheduleUuid) {
        this.scheduleUuid = scheduleUuid;
        this.scheduleContent = scheduleContent;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
    }

    public TeamScheduleDetailDto(String scheduleContent, LocalDateTime scheduleEnd, UUID scheduleUuid) {
        this.scheduleUuid = scheduleUuid;
        this.scheduleContent = scheduleContent;
        this.scheduleEnd = scheduleEnd;
    }

    public String getScheduleStart() {
        if (scheduleStart == null) {
            return null;
        }
        return scheduleStart.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getScheduleEnd() {
        if (scheduleEnd == null) {
            return null;
        }
        return scheduleEnd.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
