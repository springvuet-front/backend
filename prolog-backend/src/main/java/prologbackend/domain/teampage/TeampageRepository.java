package prologbackend.domain.teampage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeampageRepository extends JpaRepository<Teampage, UUID> {
}
