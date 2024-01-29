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

    @Column(name = "team_role")
    private String teamRole;

    @Column(name = "team_admin")
    private String teamAdmin;


    public void createRelationship(Teampage teampage, Member member) {
        this.teampage = teampage;
        this.member = member;
    }

    public void updateRelationship(Teampage teampage, Member member, String teamRole, String teamAdmin) {
        this.teampage = teampage;
        this.member = member;
        this.teamRole = teamRole;
        this.teamAdmin =teamAdmin;
    }

}
