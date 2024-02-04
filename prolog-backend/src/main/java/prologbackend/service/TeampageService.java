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
import prologbackend.dto.mypage.MyTeamResponseDetailDto;
import prologbackend.dto.mypage.MyTeamResponseDto;
import prologbackend.dto.mypage.MypageResponseDto;
import prologbackend.dto.mypage.ScheduleResponseDto;
import prologbackend.dto.teampage.*;
import prologbackend.exception.TeamNotFoundException;
import prologbackend.exception.UnauthorizedException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TeampageService {

    private final MemberRepository memberRepository;
    private final TeampageRepository teampageRepository;
    private final TeamRelationshipRepository teamRelationshipRepository;
    private final ScheduleRepository scheduleRepository;

    public TeampageService
            (MemberRepository memberRepository, TeampageRepository teampageRepository, TeamRelationshipRepository teamRelationshipRepository, ScheduleRepository scheduleRepository) {
        this.memberRepository = memberRepository;
        this.teampageRepository = teampageRepository;
        this.teamRelationshipRepository = teamRelationshipRepository;
        this.scheduleRepository = scheduleRepository;
    }

    //팀페이지 생성 -> 프로젝트명, 팀명, 프로젝트 기간, 깃허브 링크
    //팀페이지 생성시 teampage table에는 프로젝트명, 팀명, 프로젝트 기간, 깃허브 링크
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
                    (teamRequestDto.getProjectName(), teamRequestDto.getTeamName(), teamRequestDto.getStart(), teamRequestDto.getEnd(), teamRequestDto.getGithub(),teamRequestDto.getTeamPosition());
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

    //마이페이지 조회 -> 마감 임박 일정(7일 전) -> 전체 조회


    //모든 프로젝트 조회(projectname, teamname, start,end,teamrole추가(웹,앱)/teamrelation-> team_role), 진행 완료된 프로젝트
    public List<MyTeamResponseDetailDto> MyTeampage(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        UUID memberUuid = member.getMemberUuid();

        List<MyTeamResponseDetailDto> myTeampages = teamRelationshipRepository.findTeampages(memberUuid);

        return myTeampages;
    }

    //schedule조회
    public List<ScheduleResponseDto> mySchedule(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        UUID memberUuid = member.getMemberUuid();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekLater = now.plusDays(7);

        List<ScheduleResponseDto> mySchedules = scheduleRepository.findUpcomingSchedules(memberUuid, now, weekLater);

        return mySchedules;
    }

    //모든 프로젝트 + schedule 조회
    public MypageResponseDto myPage(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        UUID memberUuid = member.getMemberUuid();

        //진행 중인 프로젝트
        List<MyTeamResponseDetailDto> currentTeams = teamRelationshipRepository.findCurrentTeampages(memberUuid);
        //완료된 프로젝트
        List<MyTeamResponseDetailDto> completedTeams = teamRelationshipRepository.findCompletedTeampages(memberUuid);

        MyTeamResponseDto myTeamResponseDto = new MyTeamResponseDto(currentTeams, completedTeams);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekLater = now.plusDays(7);

        List<ScheduleResponseDto> mySchedules = scheduleRepository.findUpcomingSchedules(memberUuid, now, weekLater);

        return new MypageResponseDto(myTeamResponseDto, mySchedules);

    }

    //팀페이지 조회
    public TeampageResponseDto myTeam(UUID teampageUuid, String email) {
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

        //team members -> memberuuid 리스트에, teampageuuid는 1개
        List<TeamMembersDto> members = teamRelationshipRepository.findTeamMembers(teampageUuid);
        //team schedules
            //현재 ~ 7일 (임박 일정)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekLater = now.plusDays(7);
        LocalDateTime firstDay = now.withDayOfMonth(1).toLocalDate().atStartOfDay(); // 이 달의 첫 날
        LocalDateTime lastDay = now.withDayOfMonth(now.toLocalDate().lengthOfMonth());// 이 달의 마지막 날

        List<TeamScheduleDetailDto> keySchedules = scheduleRepository.findKeySchedules(teampageUuid,now,weekLater);
            //해당하는 달의 일정
        List<TeamScheduleDetailDto> monthSchedules = scheduleRepository.findMonthSchedules(teampageUuid,firstDay,lastDay);

        TeamScheduleDto teamScheduleDto = new TeamScheduleDto(keySchedules, monthSchedules);
        //team infomation

        TeampageDetailResponseDto teampageDetailResponseDto = teampageRepository.findTeampageBy(teampageUuid);

        long remainingDays = ChronoUnit.DAYS.between(LocalDateTime.now(), teampage.getEnd());

        teampageDetailResponseDto.setRemainingDays(remainingDays);

        return new TeampageResponseDto(members, teamScheduleDto, teampageDetailResponseDto);

    }



}
