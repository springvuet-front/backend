package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.teampage.Schedule;
import prologbackend.domain.teampage.Teampage;
import prologbackend.dto.mypage.MyTeamResponseDetailDto;
import prologbackend.dto.mypage.MypageResponseDto;
import prologbackend.dto.mypage.ScheduleResponseDto;
import prologbackend.dto.teampage.InviteDto;
import prologbackend.dto.teampage.ScheduleDto;
import prologbackend.dto.teampage.TeamRequestDto;
import prologbackend.dto.teampage.TeampageResponseDto;
import prologbackend.service.TeampageServiceImpl;

import java.util.List;
import java.util.UUID;

@Lazy
@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeampageServiceImpl teampageServiceImpl;

    public TeamController(TeampageServiceImpl teampageServiceImpl) {
        this.teampageServiceImpl = teampageServiceImpl;
    }

    //팀 페이지 관련

    //팀원초대 -> 닉네임, 역할, 권한 입력
    @PostMapping("/teampage/{teampageUuid}/create/invite")
    public ResponseEntity<Void> inviteMember(@PathVariable UUID teampageUuid, @RequestBody InviteDto inviteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        teampageServiceImpl.inviteMember(teampageUuid, inviteDto, email);
        return ResponseEntity.noContent().build();
    }

    //팀원 초대 수정
    @PutMapping("/teampage/{teampageUuid}/update/invite")
    public ResponseEntity<Void> updateMember(@PathVariable UUID teampageUuid, @RequestBody InviteDto inviteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        teampageServiceImpl.updateMember(teampageUuid, inviteDto, email);
        return ResponseEntity.noContent().build();
    }

    //팀 페이지 수정
    @PutMapping("/teampage/{teampageUuid}/edit")
    public ResponseEntity<Teampage> updateTeampage
            (@PathVariable UUID teampageUuid, @RequestBody TeamRequestDto teamRequestDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Teampage updateTeampage = teampageServiceImpl.updateTeampage(teampageUuid, teamRequestDto, email);
        return ResponseEntity.ok(updateTeampage);
    }

    //팀 스케줄 생성
    @PostMapping("/teampage/{teampageUuid}/schedule/create")
    public ResponseEntity<Schedule> createSchedule
            (@PathVariable UUID teampageUuid, @RequestBody ScheduleDto scheduleDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Schedule newSchedule = teampageServiceImpl.createSchedule(teampageUuid, scheduleDto, email);
        return ResponseEntity.ok(newSchedule);
    }

    //팀 페이지 조회 -> 팀원, 주요 일정(~7일), 모든 일정(해당하는 달), 프로젝트 마감일까지 며칠 남았는지, 프로젝트 시작일, 마감일 , 깃허브 링크
    @GetMapping("/teampage/{teampageUuid}")
    public ResponseEntity<TeampageResponseDto> teampage
            (@PathVariable UUID teampageUuid)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        TeampageResponseDto myTeam = teampageServiceImpl.myTeam(teampageUuid, email);
        return ResponseEntity.ok(myTeam);
    }


    //마이 페이지 관련

    //마이페이지에서 팀 페이지 생성
    @PostMapping("/mypage/project/create")
    public ResponseEntity<Teampage> createTeampage(@RequestBody TeamRequestDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Teampage newTeampage = teampageServiceImpl.createTeampage(request, email);
        return ResponseEntity.ok(newTeampage);
    }
    //

    //마이페이지 조회

    // 현재 진행중인 프로젝트, 진행 완료된 프로젝트 조회
    @GetMapping("/mypage/project")
    public ResponseEntity<List<MyTeamResponseDetailDto>> myTeam() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            List<MyTeamResponseDetailDto> myTeampages= teampageServiceImpl.MyTeampage(email);
            return ResponseEntity.ok(myTeampages);
    }

    //스케줄 조회
    @GetMapping("/mypage/schedule")
    public ResponseEntity<List<ScheduleResponseDto>> mySchedule() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            List<ScheduleResponseDto> mySchedules = teampageServiceImpl.mySchedule(email);
            return ResponseEntity.ok(mySchedules);
    }

    //프로젝트 + 스케줄 조회
    @GetMapping("/mypage")
    public ResponseEntity<MypageResponseDto> myPage() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            MypageResponseDto mypageResponseDto = teampageServiceImpl.myPage(email);
            return ResponseEntity.ok(mypageResponseDto);
    }

}
