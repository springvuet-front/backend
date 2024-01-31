package prologbackend.domain.teampage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prologbackend.dto.teampage.TeampageDetailResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TeampageRepository extends JpaRepository<Teampage, UUID> {
    @Query("SELECT new prologbackend.dto.teampage.TeampageDetailResponseDto(t.projectName, t.teamName, t.start, t.end, t.github) " +
            "FROM Teampage t " +
            "WHERE t.teampageUuid = :teampageUuid ")
    TeampageDetailResponseDto findTeampageBy(@Param("teampageUuid") UUID teampageUuid);
}
