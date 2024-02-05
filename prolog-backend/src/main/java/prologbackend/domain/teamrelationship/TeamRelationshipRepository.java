package prologbackend.domain.teamrelationship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prologbackend.domain.member.Member;
import prologbackend.domain.teampage.Teampage;
import prologbackend.dto.mypage.MyTeamResponseDetailDto;
import prologbackend.dto.teampage.TeamMembersDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRelationshipRepository extends JpaRepository<TeamRelationship, UUID> {
    List<TeamRelationship> findByTeampage(Teampage teampage);
    Optional<TeamRelationship> findByTeampageAndMember(Teampage teampage, Member member);

    //진행 중인 프로젝트 조회
    @Query("SELECT new prologbackend.dto.mypage.MyTeamResponseDetailDto(t.teamPosition, tr.teamRole, t.projectName, t.teamName, t.start, t.end, t.teampageUuid) " +
            "FROM TeamRelationship tr JOIN tr.teampage t " +
            "WHERE tr.member.memberUuid = :memberUuid AND t.status = true " +
            "ORDER BY t.end ASC ")
    List<MyTeamResponseDetailDto> findCurrentTeampages(UUID memberUuid);

    //끝난 프로젝트 조회
    @Query("SELECT new prologbackend.dto.mypage.MyTeamResponseDetailDto(t.teamPosition, tr.teamRole, t.projectName, t.teamName, t.start, t.end, t.teampageUuid) " +
            "FROM TeamRelationship tr JOIN tr.teampage t " +
            "WHERE tr.member.memberUuid = :memberUuid AND t.status = false " +
            "ORDER BY t.end ASC")
    List<MyTeamResponseDetailDto> findCompletedTeampages(UUID memberUuid);

    //모든 프로젝트 조회
    @Query("SELECT new prologbackend.dto.mypage.MyTeamResponseDetailDto(t.teamPosition, tr.teamRole, t.projectName, t.teamName, t.start, t.end,t.teampageUuid) " +
            "FROM TeamRelationship tr JOIN tr.teampage t " +
            "WHERE tr.member.memberUuid = :memberUuid " +
            "ORDER BY t.end ASC")
    List<MyTeamResponseDetailDto> findTeampages(UUID memberUuid);

    //팀페이지에 해당하는 Members 리스트 출력
    @Query("SELECT new prologbackend.dto.teampage.TeamMembersDto(m.nickname, tr.teamRole) " +
            "FROM TeamRelationship tr JOIN tr.member m " +
            "WHERE tr.teampage.teampageUuid = :teampageUuid " +
            "ORDER BY m.nickname ASC ")
    List<TeamMembersDto> findTeamMembers(@Param("teampageUuid") UUID teampageUuid);
}
