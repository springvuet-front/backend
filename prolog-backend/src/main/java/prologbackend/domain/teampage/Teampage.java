package prologbackend.domain.teampage;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "teamname")
    private String teamName;

    @Column(name = "teampage_start")
    private LocalDateTime start;

    @Column(name = "teampage_end")
    private LocalDateTime end;

    @Column(name = "teampage_github")
    private String github;

    @Column(name = "teampage_status")
    private boolean status;
}
