package prologbackend.dto.teampage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.teampage.Schedule;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDto {
    private String scheduleContent;
    private LocalDateTime scheduleStart;
    private LocalDateTime scheduleEnd;

    public Schedule toEntity() {
        return Schedule.builder()
                .scheduleContent(this.scheduleContent)
                .scheduleStart(this.scheduleStart)
                .scheduleEnd(this.scheduleEnd)
                .build();
    }
}
