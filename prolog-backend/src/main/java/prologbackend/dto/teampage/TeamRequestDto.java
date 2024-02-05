package prologbackend.dto.teampage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.teampage.Teampage;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TeamRequestDto {
    private String projectName;
    private String teamName;
    private String start;
    private String end;
    private String github;
    private String teamPosition;

    public Teampage toEntity() {
        LocalDate startDate = LocalDate.parse(this.start);
        LocalDate endDate = LocalDate.parse(this.end);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();

        return Teampage.builder()
                .projectName(this.projectName)
                .teamName(this.teamName)
                .start(startDateTime)
                .end(endDateTime)
                .github(this.github)
                .status(true)
                .teamPosition(this.teamPosition)
                .build();
    }

}
