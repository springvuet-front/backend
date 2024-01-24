package prologbackend.domain.teampage;

import lombok.*;
import prologbackend.domain.user.MemberRole;

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

    @Column(name = "teampage_name")
    private String teampageName;

    @Column(name = "teampage_start")
    private LocalDateTime teampageStart;

    @Column(name = "teampage_end")
    private LocalDateTime teampageEnd;

    @Column(name = "teampage_github")
    private String github;

    @Column(name = "teampage_status")
    private boolean teampageStatus;
}
