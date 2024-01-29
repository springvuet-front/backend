package prologbackend.domain.teamrelationship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prologbackend.domain.member.Member;
import prologbackend.domain.teampage.Teampage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRelationshipRepository extends JpaRepository<TeamRelationship, UUID> {
    List<TeamRelationship> findByTeampage(Teampage teampage);
    Optional<TeamRelationship> findByTeampageAndMember(Teampage teampage, Member member);
}
