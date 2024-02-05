package prologbackend.dto.teampage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.teampage.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDto {
    private String scheduleContent;
    private String scheduleStart;
    private String scheduleEnd;

    public Schedule toEntity() {
        LocalDate startDate = LocalDate.parse(this.scheduleStart);
        LocalDate endDate = LocalDate.parse(this.scheduleEnd);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();

        return Schedule.builder()
                .scheduleContent(this.scheduleContent)
                .scheduleStart(startDateTime)
                .scheduleEnd(endDateTime)
                .build();
    }
}
