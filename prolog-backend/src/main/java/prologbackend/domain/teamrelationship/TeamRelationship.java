package prologbackend.domain.teamrelationship;

import lombok.*;
import prologbackend.domain.member.Member;
import prologbackend.domain.teampage.Teampage;

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
    @JoinColumn(name = "teampage_uuid")
    private Teampage teampage;

    @ManyToOne
    @JoinColumn(name = "member_uuid")
    private Member member;

    public void createRelationship(Teampage teampage, Member member) {
        this.teampage = teampage;
        this.member = member;
    }
}
