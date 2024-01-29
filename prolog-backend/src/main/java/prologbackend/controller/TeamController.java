package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.teampage.Schedule;
import prologbackend.domain.teampage.Teampage;
import prologbackend.dto.teampage.InviteDto;
import prologbackend.dto.teampage.ScheduleDto;
import prologbackend.dto.teampage.TeamRequestDto;
import prologbackend.service.TeampageServiceImpl;

import java.util.UUID;

@Lazy
@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeampageServiceImpl teampageServiceImpl;

    public TeamController(TeampageServiceImpl teampageServiceImpl) {
        this.teampageServiceImpl = teampageServiceImpl;
    }

    @PostMapping("/mypage/project/create")
    public ResponseEntity<Teampage> createTeampage(@RequestBody TeamRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Teampage newTeampage = teampageServiceImpl.createTeampage(request, email);
        return ResponseEntity.ok(newTeampage);
    }

    @PostMapping("/teampage/{teampageUuid}/create/invite")
    public ResponseEntity<Void> inviteMember(@PathVariable UUID teampageUuid, @RequestBody InviteDto inviteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        teampageServiceImpl.inviteMember(teampageUuid, inviteDto, email);
        return ResponseEntity.noContent().build();
    }

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

}
