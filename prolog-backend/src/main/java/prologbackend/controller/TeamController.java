package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.teampage.Teampage;
import prologbackend.dto.teampage.InviteDto;
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
    public ResponseEntity<Teampage> createTeampage(@RequestBody TeamRequestDto request,@RequestHeader("Authorization") String token) {
        Teampage newTeampage = teampageServiceImpl.createTeampage(request, token);
        return ResponseEntity.ok(newTeampage);
    }

    @PostMapping("/teampage/{teampageUuid}/create/invite")
    public ResponseEntity<Void> inviteMember(@PathVariable UUID teampageUuid, @RequestBody InviteDto inviteDto, @RequestHeader("Authorization") String token) {
        teampageServiceImpl.inviteMember(teampageUuid, inviteDto, token);
        return ResponseEntity.noContent().build();
    }
}
