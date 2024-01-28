package prologbackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prologbackend.domain.member.Member;
import prologbackend.domain.member.MemberRepository;
import prologbackend.domain.teampage.Teampage;
import prologbackend.domain.teampage.TeampageRepository;
import prologbackend.domain.teamrelationship.TeamRelationship;
import prologbackend.domain.teamrelationship.TeamRelationshipRepository;
import prologbackend.dto.teampage.TeamRequestDto;
import prologbackend.jwt.TokenProvider;

@Service
@Transactional
public class TeampageServiceImpl {

    private final MemberRepository memberRepository;
    private final TeampageRepository teampageRepository;
    private final TeamRelationshipRepository teamRelationshipRepository;
    private final TokenProvider tokenProvider;

    public TeampageServiceImpl(MemberRepository memberRepository, TeampageRepository teampageRepository, TeamRelationshipRepository teamRelationshipRepository, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.teampageRepository = teampageRepository;
        this.teamRelationshipRepository = teamRelationshipRepository;
        this.tokenProvider = tokenProvider;
    }

    //팀페이지 생성 -> 프로젝트명, 팀명, 프로젝트 기간, 깃허브 링크
    //팀페이지 생성시 teampage table에는 프로젝트명, 팀명, 프로젝트 기간, 깃허브 링크
    //teampageRelation table에는 팀페이지 uuid, user uuid 동시에 올라가도록

    public Teampage createTeampage(TeamRequestDto teamRequestDto, String token) {
        Teampage newTeampage = teamRequestDto.toEntity();
        TeamRelationship relationship = new TeamRelationship();
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        relationship.createRelationship(newTeampage, member);

        teampageRepository.save(newTeampage);
        teamRelationshipRepository.save(relationship);

        return newTeampage;
    }


}
