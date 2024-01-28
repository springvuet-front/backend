package prologbackend.domain.teampage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeampageRepository extends JpaRepository<Teampage, UUID> {

}
