package prologbackend.domain.teampage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prologbackend.dto.mypage.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    //schedlue조회
    @Query("SELECT new prologbackend.dto.mypage.ScheduleResponseDto(s.scheduleContent,s.scheduleEnd) " +
            "FROM TeamRelationship tr JOIN tr.teampage t JOIN t.schedule s " +
            "WHERE tr.member.memberUuid = :memberUuid AND s.scheduleEnd between :now and :weekLater " +
            "ORDER BY s.scheduleEnd ASC")
    List<ScheduleResponseDto> findUpcomingSchedules(@Param("memberUuid") UUID memberUuid, @Param("now") LocalDateTime now, @Param("weekLater") LocalDateTime weekLater);
}

//어떤 join이 효율적일지 나중에 리팩토링 할때 생각해보기

