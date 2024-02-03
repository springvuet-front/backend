package prologbackend.dto.mypage;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ScheduleResponseDto {
    private String scheduleContent;
    private LocalDateTime scheduleEnd;

    public ScheduleResponseDto(String scheduleContent, LocalDateTime scheduleEnd) {
        this.scheduleContent = scheduleContent;
        this.scheduleEnd = scheduleEnd;
    }

    public String getScheduleEnd() {
        if (scheduleEnd == null) {
            return null;
        }
        return scheduleEnd.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
