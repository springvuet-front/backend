package prologbackend.domain.teampage;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teampage")
public class Teampage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "teampage_uuid", columnDefinition = "BINARY(16)")
    private UUID teampageUuid;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "teampage_start")
    private LocalDateTime start;

    @Column(name = "teampage_end")
    private LocalDateTime end;

    @Column(name = "teampage_github")
    private String github;

    @Column(name = "teampage_status")
    private boolean status;

    @Column(name = "team_position")
    private String teamPosition;


    //순환참조 방지 -> Teampage entity를 조회할 때만 Schedule entity들을 가져옴
    @OneToMany(mappedBy = "teampage")
    @JsonManagedReference
    private List<Schedule> schedule;

    public void update(String projectName, String teamName, LocalDateTime start, LocalDateTime end, String github,String teamPosition) {
        this.projectName = projectName;
        this.teamName = teamName;
        this.start = start;
        this.end = end;
        this.github = github;
        this.teamPosition = teamPosition;
    }
}
