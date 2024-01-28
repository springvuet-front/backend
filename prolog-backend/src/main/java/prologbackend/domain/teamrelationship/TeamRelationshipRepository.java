package prologbackend.domain.teamrelationship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRelationshipRepository extends JpaRepository<TeamRelationship, UUID> {

}
