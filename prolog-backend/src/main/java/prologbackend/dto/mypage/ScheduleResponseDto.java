package prologbackend.dto.mypage;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private String scheduleContent;
    private LocalDateTime scheduleEnd;

    public ScheduleResponseDto(String scheduleContent, LocalDateTime scheduleEnd) {
        this.scheduleContent = scheduleContent;
        this.scheduleEnd = scheduleEnd;
    }
}
