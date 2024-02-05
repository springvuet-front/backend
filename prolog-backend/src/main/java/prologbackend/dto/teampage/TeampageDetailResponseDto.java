package prologbackend.dto.teampage;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
public class TeampageDetailResponseDto {
    private String projectName;
    private String teamName;
    private LocalDateTime start;
    private LocalDateTime end;
    private String github;
    private long remainingDays;


    public TeampageDetailResponseDto(String projectName, String teamName, LocalDateTime start, LocalDateTime end, String github) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.start = start;
        this.end = end;
        this.github = github;

    }

    public void setRemainingDays(long remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getStart() {
        if (start == null) {
            return null;
        }
        return start.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getEnd() {
        if (end == null) {
            return null;
        }
        return end.format(DateTimeFormatter.ISO_DATE_TIME);
    }


}
