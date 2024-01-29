package prologbackend.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prologbackend.domain.member.Member;
import prologbackend.domain.member.MemberRepository;
import prologbackend.domain.teampage.Schedule;
import prologbackend.domain.teampage.ScheduleRepository;
import prologbackend.domain.teampage.Teampage;
import prologbackend.domain.teampage.TeampageRepository;
import prologbackend.domain.teamrelationship.TeamRelationship;
import prologbackend.domain.teamrelationship.TeamRelationshipRepository;
import prologbackend.dto.teampage.InviteDto;
import prologbackend.dto.teampage.ScheduleDto;
import prologbackend.dto.teampage.TeamRequestDto;
import prologbackend.exception.TeamNotFoundException;
import prologbackend.exception.UnauthorizedException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TeampageServiceImpl {

    private final MemberRepository memberRepository;
    private final TeampageRepository teampageRepository;
    private final TeamRelationshipRepository teamRelationshipRepository;
    private final ScheduleRepository scheduleRepository;

    public TeampageServiceImpl
            (MemberRepository memberRepository, TeampageRepository teampageRepository, TeamRelationshipRepository teamRelationshipRepository, ScheduleRepository scheduleRepository) {
        this.memberRepository = memberRepository;
        this.teampageRepository = teampageRepository;
        this.teamRelationshipRepository = teamRelationshipRepository;
        this.scheduleRepository = scheduleRepository;
    }

    //팀페이지 생성 -> 프로젝트명, 팀명, 프로젝트 기간, 깃허브 링크
    //팀페이지 생성시 teampage table에는 프로젝트명, 팀명, 프로젝트 기간, 깃허브 링크
    //teampageRelation table에는 팀페이지 uuid, user uuid 동시에 올라가도록

    public Teampage createTeampage(TeamRequestDto teamRequestDto, String email) {
        Teampage newTeampage = teamRequestDto.toEntity();
        TeamRelationship relationship = new TeamRelationship();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        relationship.createRelationship(newTeampage, member);

        teampageRepository.save(newTeampage);
        teamRelationshipRepository.save(relationship);

        return newTeampage;
    }

    //팀페이지 수정
    public Teampage updateTeampage(UUID teampageUuid, TeamRequestDto teamRequestDto, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        Teampage updateTeampage = teampageRepository.findById(teampageUuid)
                .orElseThrow(() -> new TeamNotFoundException("Post not found"));

        // 해당 사용자가 팀페이지의 멤버인지 확인
        List<TeamRelationship> relationships = teamRelationshipRepository.findByTeampage(updateTeampage);
        boolean isMember = relationships.stream()
                .anyMatch(relationship -> relationship.getMember().equals(member));

        if (!isMember) {
            throw new UnauthorizedException("You are not a member of this team");
        }
        updateTeampage.update
                    (teamRequestDto.getProjectName(), teamRequestDto.getTeamName(), teamRequestDto.getStart(), teamRequestDto.getEnd(), teamRequestDto.getGithub());
        return teampageRepository.save(updateTeampage);

    }

    //팀원 초대
    //팀원초대 -> 닉네임, 역할, 권한 입력
    public void inviteMember(UUID teampageUuid, InviteDto inviteDto, String email) {

        //팀 생성되었는지 검증
        Teampage teampage = teampageRepository.findById(teampageUuid)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        //초대하는 사람이 token가지고 있는지 검증
        Member inviter = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        for (InviteDto.Invitee inviteeInfo : inviteDto.getInvitees()) {
            Member invitee = memberRepository.findByNickname(inviteeInfo.getNickname())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            TeamRelationship relationship = new TeamRelationship();
            relationship.updateRelationship(teampage, invitee, inviteeInfo.getRole(), inviteeInfo.getAdmin());
            teamRelationshipRepository.save(relationship);

        }

    }

    //팀원 역할 수정
    public void updateMember(UUID teampageUuid, InviteDto inviteDto, String email) {
        //팀 생성되었는지 검증
        Teampage teampage = teampageRepository.findById(teampageUuid)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        //수정하는 사람이 token가지고 있는지 검증
        Member updater = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        //수정하는 사람이 팀페이지 사람인지 검증
        List<TeamRelationship> relationships = teamRelationshipRepository.findByTeampage(teampage);
        boolean isMember = relationships.stream()
                .anyMatch(relationship -> relationship.getMember().equals(updater));
        if (!isMember) {
            throw new UnauthorizedException("You are not a member of this team");
        }
        //팀페이지 사람이라면 팀원 정보 수정 로직 진행
        for (InviteDto.Invitee inviteeInfo : inviteDto.getInvitees()) {
            Member invitee = memberRepository.findByNickname(inviteeInfo.getNickname())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            TeamRelationship relationship = teamRelationshipRepository.findByTeampageAndMember(teampage, invitee)
                    .orElseThrow(() -> new EntityNotFoundException("They are not a member of this team "));

            relationship.updateRelationship(teampage,invitee,inviteeInfo.getRole(), inviteeInfo.getAdmin());
            teamRelationshipRepository.save(relationship);

        }
    }

    //스케줄 생성
    public Schedule createSchedule(UUID teampageUuid, ScheduleDto scheduleDto, String email) {

        Schedule newSchedule = scheduleDto.toEntity();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        Teampage teampage = teampageRepository.findById(teampageUuid)
                .orElseThrow(() -> new TeamNotFoundException("Post not found"));

        // 해당 사용자가 팀페이지의 멤버인지 확인
        List<TeamRelationship> relationships = teamRelationshipRepository.findByTeampage(teampage);
        boolean isMember = relationships.stream()
                .anyMatch(relationship -> relationship.getMember().equals(member));

        if (!isMember) {
            throw new UnauthorizedException("You are not a member of this team");
        }
        newSchedule.setTeampage(teampage);

        scheduleRepository.save(newSchedule);

        return newSchedule;

    }






}
