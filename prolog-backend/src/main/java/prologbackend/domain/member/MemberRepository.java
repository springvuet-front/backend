package prologbackend.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prologbackend.dto.teampage.TeamMembersDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);


}
