package prologbackend.domain.team_relationship;

import lombok.*;
import prologbackend.domain.teampage.Teampage;
import prologbackend.domain.user.Member;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_relationship")
public class TeamRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_relationship_uuid", columnDefinition = "BINARY(16)")
    private UUID relationshipUuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    @Column(name = "user_uuid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "teampage_uuid")
    @Column(name = "teampage_uuid")
    private Teampage teampage;
}

