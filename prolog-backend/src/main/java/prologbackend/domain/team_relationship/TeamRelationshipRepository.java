package prologbackend.domain.team_relationship;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRelationshipRepository extends JpaRepository<TeamRelationship, UUID> {
}
